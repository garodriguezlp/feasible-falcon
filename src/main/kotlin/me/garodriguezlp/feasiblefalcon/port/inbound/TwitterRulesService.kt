package me.garodriguezlp.feasiblefalcon.port.inbound

import me.garodriguezlp.feasiblefalcon.model.Rule
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface TwitterRulesService {

    fun getStreamFilteringRules(): Flux<Rule>

    fun addStreamFilteringRule(rule: Rule): Mono<Rule>

    fun deleteAllStreamFilteringRules(): Flux<Rule>
}
