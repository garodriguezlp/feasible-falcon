package com.example.feasiblefalcon.api

import com.example.feasiblefalcon.api.handler.FilteredStreamTwitterHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.reactive.function.server.router

@Configuration
class RouterConfig {

    @Bean
    fun router(handler: FilteredStreamTwitterHandler) = router {
        accept(APPLICATION_JSON).nest {
            "/api".nest {
                GET("/rules", handler::getRules)
            }
        }
    }

}