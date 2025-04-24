package com.api.twinme.user.domain.model

import com.api.twinme.common.exception.NotFoundProviderException
import java.time.LocalDate

class User(
    val id: Long? = -1L,
    val provider: Provider,
    val encryptedSub: String,
    val hashedSub: String,
    val email: String,
    val nickname: String,
    val birthDate: LocalDate
) {

}

enum class Provider {
    GOOGLE;

    companion object {
        fun getProvider(
            value: String
        ): Provider = try {
            Provider.valueOf(value.uppercase())
        } catch (e: Exception) {
            throw NotFoundProviderException()
        }
    }
}