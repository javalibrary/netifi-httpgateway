/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.netifi.httpgateway.bridge.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import reactor.core.publisher.UnicastProcessor;

public abstract class ByteToMessageDecoder {

  /** Cumulate {@link ByteBuf}s by merge them into one {@link ByteBuf}'s, using memory copies. */
  public static final Cumulator MERGE_CUMULATOR =
      new Cumulator() {
        @Override
        public ByteBuf cumulate(ByteBufAllocator alloc, ByteBuf cumulation, ByteBuf in) {
          try {
            final ByteBuf buffer;
            if (cumulation.writerIndex() > cumulation.maxCapacity() - in.readableBytes()
                || cumulation.refCnt() > 1
                || cumulation.isReadOnly()) {
              // Expand cumulation (by replace it) when either there is not more room in the buffer
              // or if the refCnt is greater then 1 which may happen when the user use
              // slice().retain() or
              // duplicate().retain() or if its read-only.
              //
              // See:
              // - https://github.com/netty/netty/issues/2327
              // - https://github.com/netty/netty/issues/1764
              buffer = expandCumulation(alloc, cumulation, in.readableBytes());
            } else {
              buffer = cumulation;
            }
            buffer.writeBytes(in);
            return buffer;
          } finally {
            // We must release in in all cases as otherwise it may produce a leak if writeBytes(...)
            // throw
            // for whatever release (for example because of OutOfMemoryError)
            in.release();
          }
        }
      };

  /**
   * Cumulate {@link ByteBuf}s by add them to a {@link CompositeByteBuf} and so do no memory copy
   * whenever possible. Be aware that {@link CompositeByteBuf} use a more complex indexing
   * implementation so depending on your use-case and the decoder implementation this may be slower
   * then just use the {@link #MERGE_CUMULATOR}.
   */
  public static final Cumulator COMPOSITE_CUMULATOR =
      new Cumulator() {
        @Override
        public ByteBuf cumulate(ByteBufAllocator alloc, ByteBuf cumulation, ByteBuf in) {
          ByteBuf buffer;
          try {
            if (cumulation.refCnt() > 1) {
              // Expand cumulation (by replace it) when the refCnt is greater then 1 which may
              // happen when the
              // user use slice().retain() or duplicate().retain().
              //
              // See:
              // - https://github.com/netty/netty/issues/2327
              // - https://github.com/netty/netty/issues/1764
              buffer = expandCumulation(alloc, cumulation, in.readableBytes());
              buffer.writeBytes(in);
            } else {
              CompositeByteBuf composite;
              if (cumulation instanceof CompositeByteBuf) {
                composite = (CompositeByteBuf) cumulation;
              } else {
                composite = alloc.compositeBuffer(Integer.MAX_VALUE);
                composite.addComponent(true, cumulation);
              }
              composite.addComponent(true, in);
              in = null;
              buffer = composite;
            }
            return buffer;
          } finally {
            if (in != null) {
              // We must release if the ownership was not transferred as otherwise it may produce a
              // leak if writeBytes(...) throw for whatever release (for example because of
              // OutOfMemoryError).
              in.release();
            }
          }
        }
      };

  ByteBuf cumulation;
  private Cumulator cumulator = MERGE_CUMULATOR;
  private boolean first;
  private int discardAfterReads = 16;
  private int numReads;

  /** Set the {@link Cumulator} to use for cumulate the received {@link ByteBuf}s. */
  public void setCumulator(Cumulator cumulator) {
    if (cumulator == null) {
      throw new NullPointerException("cumulator");
    }
    this.cumulator = cumulator;
  }

  /**
   * Set the number of reads after which {@link ByteBuf#discardSomeReadBytes()} are called and so
   * free up memory. The default is {@code 16}.
   */
  public void setDiscardAfterReads(int discardAfterReads) {
    if (discardAfterReads <= 0) {
      throw new IllegalArgumentException("discardAfterReads must be > 0");
    }
    this.discardAfterReads = discardAfterReads;
  }

  /**
   * Returns the actual number of readable bytes in the internal cumulative buffer of this decoder.
   * You usually do not need to rely on this value to write a decoder. Use it only when you must use
   * it at your own risk. This method is a shortcut to {@link #internalBuffer()
   * internalBuffer().readableBytes()}.
   */
  protected int actualReadableBytes() {
    return internalBuffer().readableBytes();
  }

  /**
   * Returns the internal cumulative buffer of this decoder. You usually do not need to access the
   * internal buffer directly to write a decoder. Use it only when you must use it at your own risk.
   */
  protected ByteBuf internalBuffer() {
    if (cumulation != null) {
      return cumulation;
    } else {
      return Unpooled.EMPTY_BUFFER;
    }
  }

  public void channelRead(ByteBufAllocator alloc, ByteBuf data, UnicastProcessor<Object> out) {
    try {
      first = cumulation == null;
      if (first) {
        cumulation = data;
      } else {
        cumulation = cumulator.cumulate(alloc, cumulation, data);
      }
      callDecode(cumulation, out);
    } finally {
      if (cumulation != null && !cumulation.isReadable()) {
        numReads = 0;
        cumulation.release();
        cumulation = null;
      } else if (++numReads >= discardAfterReads) {
        // We did enough reads already try to discard some bytes so we not risk to see a OOME.
        // See https://github.com/netty/netty/issues/4275
        numReads = 0;
        discardSomeReadBytes();
      }
    }
  }

  public void channelReadComplete(UnicastProcessor<Object> out) {
    if (cumulation != null) {
      callDecode(cumulation, out);
      decodeLast(cumulation, out);
    } else {
      decodeLast(Unpooled.EMPTY_BUFFER, out);
    }
    out.onComplete();
  }

  protected final void discardSomeReadBytes() {
    if (cumulation != null && !first && cumulation.refCnt() == 1) {
      // discard some bytes if possible to make more room in the
      // buffer but only if the refCnt == 1  as otherwise the user may have
      // used slice().retain() or duplicate().retain().
      //
      // See:
      // - https://github.com/netty/netty/issues/2327
      // - https://github.com/netty/netty/issues/1764
      cumulation.discardSomeReadBytes();
    }
  }

  /**
   * Called once data should be decoded from the given {@link ByteBuf}. This method will call {@link
   * #decode(ByteBuf, UnicastProcessor)} as long as decoding should take place.
   *
   * @param in the {@link ByteBuf} from which to read data
   * @param out the {@link UnicastProcessor} to which decoded messages should be added
   */
  protected void callDecode(ByteBuf in, UnicastProcessor<Object> out) {
    while (in.isReadable()) {
      int oldInputLength = in.readableBytes();
      decode(in, out);

      if (oldInputLength == in.readableBytes()) {
        break;
      }
    }
  }

  /**
   * Decode the from one {@link ByteBuf} to an other. This method will be called till either the
   * input {@link ByteBuf} has nothing to read when return from this method or till nothing was read
   * from the input {@link ByteBuf}.
   *
   * @param in the {@link ByteBuf} from which to read data
   * @param out the {@link UnicastProcessor} to which decoded messages should be added
   */
  protected abstract void decode(ByteBuf in, UnicastProcessor<Object> out);

  /**
   * By default this will just call {@link #decode(ByteBuf, UnicastProcessor)} but sub-classes may
   * override this for some special cleanup operation.
   */
  protected void decodeLast(ByteBuf in, UnicastProcessor<Object> out) {
    if (in.isReadable()) {
      // Only call decode() if there is something left in the buffer to decode.
      // See https://github.com/netty/netty/issues/4386
      decode(in, out);
    }
  }

  static ByteBuf expandCumulation(ByteBufAllocator alloc, ByteBuf cumulation, int readable) {
    ByteBuf oldCumulation = cumulation;
    cumulation = alloc.buffer(oldCumulation.readableBytes() + readable);
    cumulation.writeBytes(oldCumulation);
    oldCumulation.release();
    return cumulation;
  }

  /** Cumulate {@link ByteBuf}s. */
  public interface Cumulator {
    /**
     * Cumulate the given {@link ByteBuf}s and return the {@link ByteBuf} that holds the cumulated
     * bytes. The implementation is responsible to correctly handle the life-cycle of the given
     * {@link ByteBuf}s and so call {@link ByteBuf#release()} if a {@link ByteBuf} is fully
     * consumed.
     */
    ByteBuf cumulate(ByteBufAllocator alloc, ByteBuf cumulation, ByteBuf in);
  }
}
