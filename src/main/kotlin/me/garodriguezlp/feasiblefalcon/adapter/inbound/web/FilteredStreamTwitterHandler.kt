package me.garodriguezlp.feasiblefalcon.adapter.inbound.web

import me.garodriguezlp.feasiblefalcon.model.Rule
import me.garodriguezlp.feasiblefalcon.port.inbound.TwitterRulesService
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import reactor.core.publisher.Mono

@Component
class FilteredStreamTwitterHandler(private val twitterRulesService: TwitterRulesService) {

    fun getStreamFilteringRules(request: ServerRequest): Mono<ServerResponse> =
        ok()
            .body(twitterRulesService.getStreamFilteringRules(), Rule::class.java)

    fun addStreamFilteringRule(request: ServerRequest): Mono<ServerResponse> =
        request.bodyToMono(Rule::class.java)
            .map(twitterRulesService::addStreamFilteringRule)
            .flatMap { ok().body(it, Rule::class.java) }

    fun deleteStreamFilteringRules(request: ServerRequest): Mono<ServerResponse> =
        ok()
            .body(twitterRulesService.deleteAllStreamFilteringRules(), Rule::class.java)

}
