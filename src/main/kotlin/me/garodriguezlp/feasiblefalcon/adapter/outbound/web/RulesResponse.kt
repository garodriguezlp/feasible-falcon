package me.garodriguezlp.feasiblefalcon.adapter.outbound.web

data class RulesResponse(
    val data: List<Rule>, val meta: Meta
)

data class Rule(
    val id: String, val tag: String, val value: String
)

data class Meta(
    val resultCount: Long, val sent: String
)
