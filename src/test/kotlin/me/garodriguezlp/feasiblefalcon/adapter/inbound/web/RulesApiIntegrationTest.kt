package me.garodriguezlp.feasiblefalcon.adapter.inbound.web

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
internal class RulesApiIntegrationTest {

    @Autowired
    private lateinit var testClient: WebTestClient

    @Test
    internal fun `rules endpoint should retrieve all configured stream filtering rules`() {
        testClient.get().uri("/api/rules")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$[0].id").isEqualTo("1567277611520237572")
            .jsonPath("$[0].tag").isEqualTo("salsa")
            .jsonPath("$[0].value").isEqualTo("salsa -has:media")
    }
}