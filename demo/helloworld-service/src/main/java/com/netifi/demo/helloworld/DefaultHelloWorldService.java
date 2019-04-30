/**
 * Copyright 2018 Netifi Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.netifi.demo.helloworld;

import io.netty.buffer.ByteBuf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class DefaultHelloWorldService implements HelloWorldService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultHelloWorldService.class);

    @Override
    public Mono<HelloResponse> sayHello(HelloRequest message, ByteBuf metadata) {
        return Mono.fromSupplier(() -> {
            return HelloResponse.newBuilder()
                    .setMessage("Hello, " + message.getName() + "!")
                    .build();
        });
    }

    @Override
    public Mono<Void> sayHelloToEmptyRoom(HelloRequest message, ByteBuf metadata) {
        LOGGER.info("Hello, {}!", message.getName());
        return Mono.empty();
    }
}
