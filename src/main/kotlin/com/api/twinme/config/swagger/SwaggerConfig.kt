package com.api.twinme.config.swagger

import org.springframework.boot.info.BuildProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.ResponseEntity
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebSession
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiKey
import springfox.documentation.service.AuthorizationScope
import springfox.documentation.service.SecurityReference
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spi.service.contexts.SecurityContext
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2
import java.time.LocalTime
import java.util.*

@Configuration
@EnableSwagger2
class SwaggerConfig(
    private val buildProperties: BuildProperties
) {

    @Bean
    fun api(): Docket = Docket(DocumentationType.SWAGGER_2)
        .enable(true)
        .useDefaultResponseMessages(false)
        .directModelSubstitute(LocalTime::class.java, String::class.java)
        .ignoredParameterTypes(
            WebSession::class.java,
            ServerHttpRequest::class.java,
            ServerHttpResponse::class.java,
            ServerWebExchange::class.java,
        )
        .genericModelSubstitutes(
            Optional::class.java,
            ResponseEntity::class.java
        )
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.api.twinme"))
        .paths(PathSelectors.regex("/api/v1/.*"))
        .build()
        .apiInfo(apiInfo())
        .securityContexts(listOf(securityContext()));
//        .securitySchemes(listOf(apiKey()));

    private fun apiInfo() = ApiInfoBuilder()
        .title(buildProperties.name)
        .version("1.0")
        .build()

    // TODO : Spring Security
    private fun apiKey(): ApiKey? {
        return ApiKey("Authorization", "Authorization", "header")
    }

    private fun securityContext(): SecurityContext = SecurityContext
        .builder()
        .securityReferences(defaultAuth()).forPaths(PathSelectors.any()).build()

    private fun defaultAuth(): List<SecurityReference> {
        val authorizationScope = AuthorizationScope("global", "accessEverything")
        val authorizationScopes: Array<AuthorizationScope?> = arrayOfNulls(1)
        authorizationScopes[0] = authorizationScope
        return listOf(SecurityReference("Authorization", authorizationScopes))
    }

}