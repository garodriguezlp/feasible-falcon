package me.garodriguezlp.feasiblefalcon.port.outbound

import me.garodriguezlp.feasiblefalcon.model.Rule
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface TwitterApi {

    fun getRules(): Flux<Rule>

    fun addRule(rule: Rule): Mono<Rule>

    fun deleteRules(rules: List<Rule>): Mono<Int>
}
