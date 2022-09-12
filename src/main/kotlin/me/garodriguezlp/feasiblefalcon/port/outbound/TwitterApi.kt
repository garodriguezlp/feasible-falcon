package me.garodriguezlp.feasiblefalcon.port.outbound

import me.garodriguezlp.feasiblefalcon.model.Rule
import reactor.core.publisher.Mono

interface TwitterApi {

    fun getRules(): Mono<List<Rule>>

    fun addRule(rule: Rule): Mono<Rule>
}
