package com.api.twinme.auth.infra.jwt

import com.api.twinme.common.config.security.jwt.JwtUser
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.*
import java.util.function.Function

@Component
class JwtTokenUtils(
    @Value("\${jwt.secret}") private val secretKey: String,
    @Value("\${jwt.token-expiration}") private val tokenExpiration: Long,
    @Value("\${jwt.refresh-token-expiration}") private val refreshTokenExpiration: Long,
) {

    companion object {
        const val header = "Authorization"
    }

    fun getSubFromToken(
        token: String
    ): String? {
        return getClaimFromToken(token, Function { obj: Claims -> obj.subject })
    }

    private fun <T> getClaimFromToken(
        token: String,
        claimsResolver: Function<Claims, T>
    ): T {
        val claims: Claims = getAllClaimsFromToken(token)
        return claimsResolver.apply(claims)
    }

    fun getAllClaimsFromToken(
        token: String
    ): Claims = Jwts.parser()
        .setSigningKey(secretKey)
        .parseClaimsJws(token)
        .body

    fun validateToken(
        token: String,
        userDetails: UserDetails
    ): Boolean {
        val user = userDetails as JwtUser
        val username = getSubFromToken(token)
        val created = getIssuedAtDateFromToken(token)
        return username == user.username && !isTokenExpired(token)
    }

    private fun getIssuedAtDateFromToken(
        token: String
    ): Date = getClaimFromToken(token, Function { obj: Claims -> obj.issuedAt })

    private fun isTokenExpired(
        token: String
    ): Boolean {
        val expiration = getExpirationDateFromToken(token)
        return expiration.before(Date())
    }

    private fun getExpirationDateFromToken(
        token: String
    ): Date = getClaimFromToken(token, Function { obj: Claims -> obj.expiration })

    fun generateToken(
        username: String,
        jwtUser: JwtUser
    ): String {
        val claims = mutableMapOf<String, Any>()
        claims["authority"] = jwtUser.authorities
        return doGenerateToken(claims, jwtUser.username, jwtUser.getId())
    }

    private fun doGenerateToken(
        claims: Map<String, Any>,
        subject: String,
        userId: Long
    ): String {
        val now = Date()
        val expirationDate = calculateTokenExpirationDate(now)
        return Jwts.builder()
            .setClaims(claims)
            .setSubject(subject)
            .setId(userId.toString())
            .setIssuedAt(now)
            .setExpiration(expirationDate)
            .signWith(SignatureAlgorithm.HS512, secretKey)
            .compact()
    }

    fun generateRefreshToken(
        username: String,
        jwtUser: JwtUser
    ): String {
        val claims = mutableMapOf<String, Any>()
        claims["authority"] = jwtUser.authorities
        return doGenerateRefreshToken(claims, jwtUser.username, jwtUser.getId())
    }

    private fun doGenerateRefreshToken(
        claims: Map<String, Any>,
        subject: String,
        userId: Long
    ): String {
        val now = Date()
        val expirationDate = calculateRefreshTokenExpirationDate(now)
        return Jwts.builder()
            .setClaims(claims)
            .setSubject(subject)
            .setId(userId.toString())
            .setIssuedAt(now)
            .setExpiration(expirationDate)
            .signWith(SignatureAlgorithm.HS512, secretKey)
            .compact()
    }

    private fun calculateTokenExpirationDate(
        createdDate: Date
    ): Date {
        return Date(createdDate.time + tokenExpiration * 1000)
    }

    private fun calculateRefreshTokenExpirationDate(
        createdDate: Date
    ): Date {
        return Date(createdDate.time + refreshTokenExpiration * 1000)
    }

    fun getUserIdFromToken(
        token: String
    ): Long {
        return getClaimFromToken(token, Function { obj: Claims -> obj.id }).toLong()
    }

}