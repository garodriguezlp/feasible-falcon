package me.garodriguezlp.feasiblefalcon.service

import me.garodriguezlp.feasiblefalcon.model.Rule
import me.garodriguezlp.feasiblefalcon.port.inbound.TwitterRulesService
import me.garodriguezlp.feasiblefalcon.port.outbound.TwitterApi
import org.springframework.stereotype.Service

@Service
class TwitterRulesServiceImpl(private val twitterApi: TwitterApi) : TwitterRulesService {

    override fun getStreamFilteringRules() = twitterApi.getRules()

    override fun addStreamFilteringRule(rule: Rule) = twitterApi.addRule(rule)
}