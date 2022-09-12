package me.garodriguezlp.feasiblefalcon.adapter.outbound.web

import me.garodriguezlp.feasiblefalcon.model.Rule
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.json.BasicJsonTester
import org.springframework.web.reactive.function.client.WebClient
import reactor.test.StepVerifier

internal class TwitterApiImplTest {

    private val bearerToken = "bearer-token"

    private lateinit var mockWebServer: MockWebServer
    private lateinit var twitterApiImpl: TwitterApiImpl

    private val jsonTester = BasicJsonTester(this::class.java)

    @BeforeEach
    internal fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start(0)
        val webClient = WebClient.builder()
            .baseUrl("http://localhost:${mockWebServer.port}")
            .build()
        twitterApiImpl = TwitterApiImpl(webClient, bearerToken)
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
            .verifyComplete()

        val takeRequest = mockWebServer.takeRequest()
        assertThat(takeRequest)
            .extracting("path", "method")
            .containsExactly("/tweets/search/stream/rules", "GET")

        assertThat(takeRequest.getHeader("Authorization"))
            .isEqualTo("Bearer $bearerToken")
    }

    @Test
    internal fun `should hit twitter api to add a new rule`() {
        val body = this::class.java.getResource("/__files/twitter-add-stream-rule-response.json")!!.readText()
        mockWebServer.enqueue(MockResponse().setBody(body).addHeader("Content-Type", "application/json"))

        StepVerifier.create(twitterApiImpl.addRule(Rule("politics", "#politics #colombia -has:media")))
            .expectNextMatches { it.id == "1568377031330496514" && it.tag == "politics" && it.value == "#politics #colombia -has:media" }
            .verifyComplete();

        val takeRequest = mockWebServer.takeRequest()
        assertThat(takeRequest)
            .extracting(RecordedRequest::path, RecordedRequest::method, { it.getHeader("Authorization") })
            .containsExactly("/tweets/search/stream/rules", "POST", "Bearer $bearerToken")
        assertThat(jsonTester.from(takeRequest.body.readUtf8()))
            .isEqualToJson("""{"add":[{"value":"#politics #colombia -has:media","tag":"politics"}]}""")
    }
}