package me.garodriguezlp.feasiblefalcon.adapter.inbound.web

import me.garodriguezlp.feasiblefalcon.port.inbound.TwitterPort
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.HttpStatus
import org.springframework.web.reactive.function.server.EntityResponse
import org.springframework.web.reactive.function.server.ServerRequest
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

@ExtendWith(MockitoExtension::class)
internal class FilteredStreamTwitterHandlerTest {

    @Mock
    lateinit var twitterPort: TwitterPort;

    @InjectMocks
    lateinit var handler: FilteredStreamTwitterHandler;

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