package me.garodriguezlp.feasiblefalcon.model

data class Rule(
    val id: String, val tag: String, val value: String
){
    constructor(tag: String, value: String) : this("", tag, value)
}
