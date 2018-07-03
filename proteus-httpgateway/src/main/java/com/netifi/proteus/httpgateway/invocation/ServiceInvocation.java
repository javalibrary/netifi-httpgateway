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
package com.netifi.proteus.httpgateway.invocation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.util.JsonFormat;
import io.netifi.proteus.rsocket.ProteusSocket;
import reactor.core.publisher.Mono;

import java.lang.reflect.Method;
import java.util.List;

public class ServiceInvocation {
    private final ProteusSocket proteusSocket;
    private final Object client;
    private final Method methodToInvoke;
    private final List<Class<?>> parameterTypes;
    private final List<Object> parameters;
    private final Class<?> responseType;

    public ServiceInvocation(ProteusSocket proteusSocket,
                             Object client,
                             Method methodToInvoke,
                             List<Class<?>> parameterTypes,
                             List<Object> parameters,
                             Class<?> responseType) {
        this.proteusSocket = proteusSocket;
        this.client = client;
        this.methodToInvoke = methodToInvoke;
        this.parameterTypes = parameterTypes;
        this.parameters = parameters;
        this.responseType = responseType;
    }

    public Mono<ServiceInvocationResult> invoke() {
        return Mono.fromSupplier(() -> {
            System.out.println(client);
            System.out.println(responseType);
            return new ServiceInvocationResult();
        });
    }
}
