package me.garodriguezlp.feasiblefalcon.adapter.outbound.web

import me.garodriguezlp.feasiblefalcon.model.Rule
import me.garodriguezlp.feasiblefalcon.port.outbound.TwitterApi
import org.slf4j.LoggerFactory.getLogger
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class TwitterApiImpl(
    private var webClient: WebClient,

    @Value("\${twitter.api.bearer.token}")
    private var bearerToken: String
) : TwitterApi {

    private val tweetsSearchStreamRulesApiPath = "/tweets/search/stream/rules"

    private val logger = getLogger(TwitterApiImpl::class.java)

    override fun getRules(): Flux<Rule> =
        webClient.get()
            .uri(tweetsSearchStreamRulesApiPath)
            .headers { it.setBearerAuth(bearerToken) }
            .retrieve()
            .bodyToMono<RulesResponse>()
            .flatMapIterable { mapToRuleList(it) }

    override fun addRule(rule: Rule): Mono<Rule> =
        webClient.post()
            .uri(tweetsSearchStreamRulesApiPath)
            .headers { it.setBearerAuth(bearerToken) }
            .bodyValue(mapToAddRequest(rule))
            .retrieve()
            .bodyToMono<RulesResponse>()
            .map { mapToRule(it) }

    override fun deleteRules(rules: List<Rule>): Mono<Int> {
        logger.info("Deleting rules: $rules")
        return webClient.post()
            .uri(tweetsSearchStreamRulesApiPath)
            .headers { it.setBearerAuth(bearerToken) }
            .bodyValue(mapToDeleteRequest(rules))
            .retrieve()
            .bodyToMono<RulesResponse>()
            .map { it.meta.summary.deleted }
    }

    private fun mapToRuleList(response: RulesResponse) =
        response.data.map { Rule(it.id, it.tag, it.value) }

    private fun mapToAddRequest(rule: Rule) =
        AddRuleRequest(listOf(TwitterRule(rule.tag, rule.value)))

    private fun mapToRule(response: RulesResponse) =
        response.data[0].let { Rule(it.id, it.tag, it.value) }

    private fun mapToDeleteRequest(rules: List<Rule>): DeleteRulesRequest {
        return DeleteRulesRequest(DeleteRule(rules.map { it.id }))
    }
}
