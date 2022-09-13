package me.garodriguezlp.feasiblefalcon.service

import me.garodriguezlp.feasiblefalcon.model.Rule
import me.garodriguezlp.feasiblefalcon.port.inbound.TwitterRulesService
import me.garodriguezlp.feasiblefalcon.port.outbound.TwitterApi
import org.slf4j.LoggerFactory.getLogger
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class TwitterRulesServiceImpl(private val twitterApi: TwitterApi) : TwitterRulesService {

    private val logger = getLogger(TwitterRulesServiceImpl::class.java)

    override fun getStreamFilteringRules(): Flux<Rule> = twitterApi.getRules()

    override fun addStreamFilteringRule(rule: Rule): Mono<Rule> = twitterApi.addRule(rule)

    override fun deleteAllStreamFilteringRules(): Flux<Rule> =
        twitterApi.getRules()
            .collectList()
            .doOnNext { logger.info("Deleting rules: $it") }
            .doOnNext(twitterApi::deleteRules)
            .flatMapIterable { it }
}