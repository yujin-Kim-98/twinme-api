package com.api.twinme.auth.adapter.out.persistence.repository

import com.api.twinme.auth.adapter.out.persistence.entity.UserTokenEntity
import org.springframework.data.repository.CrudRepository

interface UserTokenRedisRepository : CrudRepository<UserTokenEntity, Long>