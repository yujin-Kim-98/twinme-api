package com.api.twinme.auth.service

import com.api.twinme.auth.domain.User
import com.api.twinme.auth.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserPersistService(
    private val userRepository: UserRepository
) {

    @Transactional
    fun createUser(
        user: User
    ): User {
        return userRepository.save(user)
    }

}