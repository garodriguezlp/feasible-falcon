package com.example.feasiblefalcon.handler

import com.example.feasiblefalcon.api.handler.FilteredStreamTwitterHandler
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.springframework.http.HttpStatus
import org.springframework.web.reactive.function.server.EntityResponse
import org.springframework.web.reactive.function.server.ServerRequest
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

internal class FilteredStreamTwitterHandlerTest {

    private val handler = FilteredStreamTwitterHandler()

    @Test
    fun getRulesShouldReturn200StatusCodeAndHelloWorldBody() {
        val serverRequest = mock(ServerRequest::class.java)
        StepVerifier.create(handler.getRules(serverRequest))
            .assertNext { response ->
                assertThat(response.statusCode()).isEqualTo(HttpStatus.OK)
                StepVerifier.create((response as EntityResponse<*>).entity() as Mono<*>)
                    .assertNext { assertThat(it).isEqualTo("Hello world") }
                    .verifyComplete()
            }
            .verifyComplete()
    }

}