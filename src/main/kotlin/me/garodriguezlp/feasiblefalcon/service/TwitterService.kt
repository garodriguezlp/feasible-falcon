package me.garodriguezlp.feasiblefalcon.service

import me.garodriguezlp.feasiblefalcon.port.inbound.TwitterPort
import org.springframework.stereotype.Service

@Service
class TwitterService : TwitterPort {

    override fun getTweets(): List<String> {
        TODO("Not yet implemented")
    }
}