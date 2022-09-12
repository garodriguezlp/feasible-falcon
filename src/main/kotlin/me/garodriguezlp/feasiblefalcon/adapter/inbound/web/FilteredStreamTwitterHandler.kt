package me.garodriguezlp.feasiblefalcon.adapter.inbound.web

import me.garodriguezlp.feasiblefalcon.model.Rule
import me.garodriguezlp.feasiblefalcon.port.inbound.TwitterRulesService
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse.ok

@Component
class FilteredStreamTwitterHandler(private val twitterRulesService: TwitterRulesService) {

    fun getStreamFilteringRules(request: ServerRequest) = ok()
        .body(twitterRulesService.getStreamFilteringRules(), List::class.java)

    fun addStreamFilteringRule(request: ServerRequest) = request.bodyToMono(Rule::class.java)
        .map { twitterRulesService.addStreamFilteringRule(it) }
        .flatMap { ok().body(it, Rule::class.java) }

}
