package me.garodriguezlp.feasiblefalcon.service

import me.garodriguezlp.feasiblefalcon.model.Rule
import me.garodriguezlp.feasiblefalcon.port.inbound.TwitterRulesService
import me.garodriguezlp.feasiblefalcon.port.outbound.TwitterApi
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class TwitterRulesServiceImpl(private val twitterApi: TwitterApi) : TwitterRulesService {

    override fun getStreamFilteringRules(): Mono<List<Rule>> {
        return twitterApi.getRules()
    }
}