package me.garodriguezlp.feasiblefalcon.adapter.inbound.web

import me.garodriguezlp.feasiblefalcon.model.Rule
import me.garodriguezlp.feasiblefalcon.port.inbound.TwitterRulesService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.HttpStatus
import org.springframework.web.reactive.function.server.EntityResponse
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

@ExtendWith(MockitoExtension::class)
internal class FilteredStreamTwitterHandlerTest {

    @Mock
    lateinit var twitterRulesService: TwitterRulesService;

    @InjectMocks
    lateinit var handler: FilteredStreamTwitterHandler;

    @Test
    fun `get rules should return 200 and the list of all configured rules`() {
        val serverRequest = mock(ServerRequest::class.java)
        val rule = Rule("id", "tag", "value")

        `when`(twitterRulesService.getStreamFilteringRules()).thenReturn(Mono.just(listOf(rule)))

        StepVerifier.create(handler.getStreamFilteringRules(serverRequest))
            .assertNext { response ->
                assertThat(response.statusCode()).isEqualTo(HttpStatus.OK)
                StepVerifier.create(extractResponseEntity(response))
                    .assertNext { assertThat(it as List<*>).containsOnly(rule) }
                    .verifyComplete()
            }
            .verifyComplete()
    }

    @Test
    fun `add rule should return 200 and the added rule`() {
        val serverRequest = mock(ServerRequest::class.java)
        val rule = Rule("id", "tag", "value")

        `when`(serverRequest.bodyToMono(Rule::class.java)).thenReturn(Mono.just(rule))
        `when`(twitterRulesService.addStreamFilteringRule(rule)).thenReturn(Mono.just(rule))

        StepVerifier.create(handler.addStreamFilteringRule(serverRequest))
            .assertNext { response ->
                assertThat(response.statusCode()).isEqualTo(HttpStatus.OK)
                StepVerifier.create(extractResponseEntity(response))
                    .assertNext { assertThat(it as Rule).isEqualTo(rule) }
                    .verifyComplete()
            }
            .verifyComplete()
    }

    private fun extractResponseEntity(response: ServerResponse?) =
        (response as EntityResponse<*>).entity() as Mono<*>
}