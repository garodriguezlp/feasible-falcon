package me.garodriguezlp.feasiblefalcon.adapter.outbound.web

data class RulesResponse(
    val data: List<TwitterRule>, val meta: Meta
)

data class TwitterRule(
    val id: String, val tag: String, val value: String
) {
    constructor(tag: String, value: String) : this("", tag, value)
}

data class Meta(
    val sent: String
)

data class AddRule(
    val add: List<TwitterRule>
)
