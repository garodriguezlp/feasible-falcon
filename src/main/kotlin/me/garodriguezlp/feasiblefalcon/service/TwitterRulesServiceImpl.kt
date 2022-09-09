package me.garodriguezlp.feasiblefalcon.service

import me.garodriguezlp.feasiblefalcon.port.inbound.TwitterRulesService
import me.garodriguezlp.feasiblefalcon.port.outbound.TwitterApi
import org.springframework.stereotype.Service

@Service
class TwitterRulesServiceImpl(private val twitterApi: TwitterApi) : TwitterRulesService {

    override fun getTweets(): List<String> {
        TODO("Not yet implemented")
    }
}