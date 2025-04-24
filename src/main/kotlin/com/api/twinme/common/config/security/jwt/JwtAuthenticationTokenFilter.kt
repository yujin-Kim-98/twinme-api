package com.api.twinme.common.config.security.jwt

import com.api.twinme.auth.infra.jwt.JwtTokenUtils
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtAuthenticationTokenFilter(
    private val userDetailsService: UserDetailsService,
    private val jwtTokenUtils: JwtTokenUtils
): OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
//        val header = "Authorization"
        val requestHeader: String? = request.getHeader(JwtTokenUtils.header)

        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            val authToken = requestHeader.substring(7)
            val sub = jwtTokenUtils.getSubFromToken(authToken)

            if (sub != null && SecurityContextHolder.getContext().authentication == null) {
                val userDetails = userDetailsService.loadUserByUsername(sub)
                if (jwtTokenUtils.validateToken(authToken, userDetails)) {
                    val authentication = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
                    SecurityContextHolder.getContext().authentication = authentication
                }
            }
        }

        filterChain.doFilter(request, response)
    }

}