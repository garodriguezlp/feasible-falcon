package me.garodriguezlp.feasiblefalcon.adapter.inbound.web

import me.garodriguezlp.feasiblefalcon.port.inbound.TwitterRulesService
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class FilteredStreamTwitterHandler(private val twitterRulesService: TwitterRulesService) {

    fun getStreamFilteringRules(request: ServerRequest): Mono<ServerResponse> {
        return ServerResponse.ok()
            .body(twitterRulesService.getStreamFilteringRules(), List::class.java)
    }

}
