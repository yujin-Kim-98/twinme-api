package com.api.twinme.config.web

import com.api.twinme.auth.utils.JwtTokenUtils
import com.api.twinme.auth.repository.UserRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.MethodParameter
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport

@Configuration
class WebMvcConfig(
    private val jwtTokenUtils: JwtTokenUtils,
    private val userRepository: UserRepository
): WebMvcConfigurationSupport() {

    @Bean
    fun corsFilter(): CorsFilter? {
        val source = UrlBasedCorsConfigurationSource()

        val config = CorsConfiguration()
        config.allowCredentials = true
        config.allowedOriginPatterns = listOf("*")
        config.addAllowedHeader("*")
        config.addAllowedMethod("*")
        source.registerCorsConfiguration("/**", config)
        return CorsFilter(source)
    }

    override fun addArgumentResolvers(
        argumentResolvers: MutableList<HandlerMethodArgumentResolver>
    ) {
        argumentResolvers.add(
            AuthenticationTokenResolver(jwtTokenUtils, userRepository)
        )
    }

    override fun addResourceHandlers(
        registry: ResourceHandlerRegistry
    ) {
        registry.addResourceHandler("/swagger-ui.html**")
            .addResourceLocations("classpath:/META-INF/resources/")

        registry.addResourceHandler("/webjars/**")
            .addResourceLocations("classpath:/META-INF/resources/webjars/")
    }

}

class AuthenticatedUser(
    val userId: Long,
    val token: String
)

class AuthenticationTokenResolver(
    private val jwtTokenUtils: JwtTokenUtils,
    private val userRepository: UserRepository
): HandlerMethodArgumentResolver {

    override fun supportsParameter(
        parameter: MethodParameter
    ): Boolean {
        return parameter.parameterType == AuthenticatedUser::class.java
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Any {
        val token = webRequest.getToken()
        val userId = jwtTokenUtils.getUserIdFromToken(token)

        return AuthenticatedUser(
            userId = userId,
            token = token
        )
    }

    private fun NativeWebRequest.getToken(): String {
        return getHeader(JwtTokenUtils.header)?.substring(7) ?: ""
    }

}