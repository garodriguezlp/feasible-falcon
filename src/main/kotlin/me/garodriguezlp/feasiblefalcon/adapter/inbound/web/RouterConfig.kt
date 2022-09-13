package me.garodriguezlp.feasiblefalcon.adapter.inbound.web

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import me.garodriguezlp.feasiblefalcon.model.Rule
import org.springdoc.core.annotations.RouterOperation
import org.springdoc.core.annotations.RouterOperations
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.RequestMethod.GET
import org.springframework.web.reactive.function.server.router

@Configuration
class RouterConfig {


    @Bean
    @RouterOperations(
        value = [
            RouterOperation(
                path = "/api/rules",
                method = [GET],
                produces = [APPLICATION_JSON_VALUE],
                operation = Operation(
                    operationId = "getRules",
                    summary = "Get all streaming filtering rules",
                    tags = ["Rules"],
                    responses = [ApiResponse(
                        responseCode = "200",
                        description = "OK",
                        content = [Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = Schema(implementation = Rule::class)
                        )]
                    )]
                )
            )
        ]
    )
    fun router(handler: FilteredStreamTwitterHandler) = router {
        accept(APPLICATION_JSON).nest {
            "/api".nest {
                GET("/rules", handler::getStreamFilteringRules)
                POST("/rule", handler::addStreamFilteringRule)
                DELETE("/rules", handler::deleteStreamFilteringRules)
            }
        }
    }
}