package me.garodriguezlp.feasiblefalcon.adapter.inbound.web

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest
@AutoConfigureWebTestClient
internal class RulesApiIntegrationTest {

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