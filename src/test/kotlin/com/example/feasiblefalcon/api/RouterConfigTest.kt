package com.example.feasiblefalcon.api

import com.example.feasiblefalcon.api.handler.FilteredStreamTwitterHandler
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.reactive.server.WebTestClient

@WebFluxTest
@ContextConfiguration(classes = [RouterConfig::class, FilteredStreamTwitterHandler::class])
class RouterConfigTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Test
    internal fun `rules endpoint should retrieve all configured stream filtering rules`() {
        webTestClient.get().uri("/api/rules")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$").isEqualTo("Hello world")
    }
}