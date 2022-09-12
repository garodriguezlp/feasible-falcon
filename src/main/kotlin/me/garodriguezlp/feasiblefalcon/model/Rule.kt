package me.garodriguezlp.feasiblefalcon.model

import com.fasterxml.jackson.annotation.JsonCreator

data class Rule(
    val id: String, val tag: String, val value: String
) {
    @JsonCreator
    constructor(tag: String, value: String) : this("", tag, value)
}
