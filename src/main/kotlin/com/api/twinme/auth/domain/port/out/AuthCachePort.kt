package com.api.twinme.auth.domain.port.out

import com.api.twinme.auth.domain.model.UserToken
import com.api.twinme.user.domain.model.User

interface AuthCachePort {

    fun saveRefreshToken(
        user: User,
        userToken: UserToken
    )

}