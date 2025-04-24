package com.api.twinme.chat.repository

import com.api.twinme.chat.entity.ChatMessageEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ChatMessageJpaRepository: JpaRepository<ChatMessageEntity, Long> {

    fun findAllTop20ByUserIdAndIdLessThanOrderByCreatedAtDesc(
        userId: Long,
        cursor: Long,
    ): List<ChatMessageEntity>

}