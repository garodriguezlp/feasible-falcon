package me.garodriguezlp.feasiblefalcon.adapter.outbound.web

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class TwitterApiConfig {

    @Value("\${twitter.api.url}")
    lateinit var twitterApiBaseUrl: String

    @Bean
    fun webClient(): WebClient {
        return WebClient.create(twitterApiBaseUrl)
    }
}