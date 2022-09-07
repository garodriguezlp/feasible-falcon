package com.example.feasiblefalcon.api.handler

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class FilteredStreamTwitterHandler {

    fun getRules(request: ServerRequest): Mono<ServerResponse> {
        return ServerResponse.ok()
            .body(Mono.just("Hello world"), String::class.java)
    }

}
