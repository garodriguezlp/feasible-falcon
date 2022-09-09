package me.garodriguezlp.feasiblefalcon.adapter.outbound.web

import me.garodriguezlp.feasiblefalcon.model.Rule
import me.garodriguezlp.feasiblefalcon.port.outbound.TwitterApi
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class TwitterApiImpl(private var webClient: WebClient,
                     @Value("\${twitter.api.bearer.token}")
                     private var bearerToken : String) : TwitterApi {

    override fun getRules(): Mono<List<Rule>> {
        return webClient.get()
            .uri("/tweets/search/stream/rules")
            .headers { it.setBearerAuth(bearerToken) }
            .retrieve()
            .bodyToMono<RulesResponse>()
            .map { mapToRule(it) }
    }

    private fun mapToRule(response: RulesResponse): List<Rule> {
        return response.data.map { Rule(it.id, it.tag, it.value) }
    }
}
