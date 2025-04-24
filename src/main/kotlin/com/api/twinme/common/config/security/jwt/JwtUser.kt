package com.api.twinme.common.config.security.jwt

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.LocalDate

class JwtUser(
    private val id: Long,
    private val sub: String,
    private val nickname: String,
    private val email: String,
    private val birthDate: LocalDate,
    private val authorities: MutableList<out GrantedAuthority>
): UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = authorities

    override fun getPassword(): String = password

    override fun getUsername(): String = sub

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true

    fun getId(): Long = id

}