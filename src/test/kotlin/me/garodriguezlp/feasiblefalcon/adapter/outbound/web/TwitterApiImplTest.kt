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
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start(0)
        val webClient = WebClient.builder()
            .baseUrl("http://localhost:${mockWebServer.port}")
            .build()
        twitterApiImpl = TwitterApiImpl(webClient, bearerToken)
    }

    @AfterEach
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `should hit twitter api to get all configured routes`() {
        val body = this::class.java.getResource("/__files/twitter-search-stream-rules-response.json")!!.readText()
        mockWebServer.enqueue(MockResponse().setBody(body).addHeader("Content-Type", "application/json"))

        StepVerifier.create(twitterApiImpl.getRules())
            .expectNext(Rule("1567277611520237572", "salsa", "salsa -has:media"))
            .verifyComplete()

        assertThat(mockWebServer.takeRequest())
            .extracting(RecordedRequest::path, RecordedRequest::method, { it.getHeader("Authorization") })
            .containsExactly("/tweets/search/stream/rules", "GET", "Bearer $bearerToken")
    }

    @Test
    fun `should hit twitter api to add a new rule`() {
        val body = this::class.java.getResource("/__files/twitter-add-stream-rule-response.json")!!.readText()
        mockWebServer.enqueue(MockResponse().setBody(body).addHeader("Content-Type", "application/json"))

        StepVerifier.create(twitterApiImpl.addRule(Rule("politics", "#politics #colombia -has:media")))
            .expectNext(Rule("1568377031330496514", "politics", "#politics #colombia -has:media"))
            .verifyComplete();

        val takeRequest = mockWebServer.takeRequest()
        assertThat(takeRequest)
            .extracting(RecordedRequest::path, RecordedRequest::method, { it.getHeader("Authorization") })
            .containsExactly("/tweets/search/stream/rules", "POST", "Bearer $bearerToken")
        assertThat(jsonTester.from(takeRequest.body.readUtf8()))
            .isEqualToJson("""{"add":[{"value":"#politics #colombia -has:media","tag":"politics"}]}""")
    }

    @Test
    fun `should hit twitter api to delete all given rules`() {
        val body = this::class.java.getResource("/__files/twitter-delete-stream-rules-response.json")!!.readText()
        mockWebServer.enqueue(MockResponse().setBody(body).addHeader("Content-Type", "application/json"))

        val deletedRules = twitterApiImpl.deleteRules(listOf(Rule("1567277611520237572", "test1", "#test1")))

        StepVerifier.create(deletedRules)
            .expectNext(1)
            .verifyComplete()

        val takeRequest = mockWebServer.takeRequest()
        assertThat(takeRequest)
            .extracting(RecordedRequest::path, RecordedRequest::method, { it.getHeader("Authorization") })
            .containsExactly("/tweets/search/stream/rules", "POST", "Bearer $bearerToken")
        assertThat(jsonTester.from(takeRequest.body.readUtf8()))
            .isEqualToJson("""{"delete":{"ids":["1567277611520237572"]}}""")
    }
}