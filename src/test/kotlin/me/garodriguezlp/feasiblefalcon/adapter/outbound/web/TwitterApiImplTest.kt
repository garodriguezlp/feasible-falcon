package me.garodriguezlp.feasiblefalcon.adapter.outbound.web

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.web.reactive.function.client.WebClient
import reactor.test.StepVerifier

internal class TwitterApiImplTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var twitterApiImpl: TwitterApiImpl

    @BeforeEach
    internal fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start(0)
        val webClient = WebClient.builder()
            .baseUrl("http://localhost:${mockWebServer.port}")
            .build()
        twitterApiImpl = TwitterApiImpl(webClient, "Bearer token")
    }

    @AfterEach
    internal fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    internal fun `should hit twitter api to get all configured routes`() {
        val body = this::class.java.getResource("/__files/twitter-search-stream-rules-response.json")!!.readText()

        mockWebServer.enqueue(MockResponse().setBody(body).addHeader("Content-Type", "application/json"))

        StepVerifier.create(twitterApiImpl.getRules())
            .expectNextMatches { it[0].id == "1567277611520237572" && it[0].tag == "salsa" && it[0].value == "salsa -has:media" }
            .verifyComplete();
    }
}