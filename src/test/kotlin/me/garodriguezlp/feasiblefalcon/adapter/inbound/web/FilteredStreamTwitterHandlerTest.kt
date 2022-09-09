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
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

@ExtendWith(MockitoExtension::class)
internal class FilteredStreamTwitterHandlerTest {

    @Mock
    lateinit var twitterRulesService: TwitterRulesService;

    @InjectMocks
    lateinit var handler: FilteredStreamTwitterHandler;

    @Test
    fun getRulesShouldReturn200StatusCodeAndHelloWorldBody() {
        val serverRequest = mock(ServerRequest::class.java)
        val rule = Rule("id", "tag", "value")

        `when`(twitterRulesService.getStreamFilteringRules()).thenReturn(Mono.just(listOf(rule)))

        StepVerifier.create(handler.getStreamFilteringRules(serverRequest))
            .assertNext { response ->
                assertThat(response.statusCode()).isEqualTo(HttpStatus.OK)
                StepVerifier.create((response as EntityResponse<*>).entity() as Mono<*>)
                    .assertNext { assertThat(it as List<*>).containsOnly(rule) }
                    .verifyComplete()
            }
            .verifyComplete()
    }

}