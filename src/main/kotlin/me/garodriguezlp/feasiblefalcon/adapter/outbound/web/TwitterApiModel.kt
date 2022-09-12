package me.garodriguezlp.feasiblefalcon.adapter.outbound.web

import com.fasterxml.jackson.annotation.JsonSetter
import com.fasterxml.jackson.annotation.Nulls.SKIP

data class RulesResponse(
    @JsonSetter(nulls = SKIP)
    val data: List<TwitterRule> = emptyList(),

    val meta: Meta
)

data class TwitterRule(
    val id: String,
    val tag: String,
    val value: String
) {
    constructor(tag: String, value: String) : this("", tag, value)
}

data class Meta(
    val sent: String,

    @JsonSetter(nulls = SKIP)
    val summary: Summary = Summary(0)
)

class Summary(
    val deleted: Int
)

data class AddRuleRequest(
    val add: List<TwitterRule>
)

data class DeleteRulesRequest(
    val delete: DeleteRule
)

class DeleteRule(
    val ids: List<String>
)
