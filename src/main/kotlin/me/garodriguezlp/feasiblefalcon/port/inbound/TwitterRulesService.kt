package me.garodriguezlp.feasiblefalcon.port.inbound

import me.garodriguezlp.feasiblefalcon.model.Rule
import reactor.core.publisher.Mono

interface TwitterRulesService {

    fun getStreamFilteringRules(): Mono<List<Rule>>
}
