package com.prasanna.skoproject.data.mapper

import com.prasanna.skoproject.data.local.UserEntity
import com.prasanna.skoproject.data.remote.UserDto
import com.prasanna.skoproject.domain.model.User

fun UserDto.toUserEntity():UserEntity{
    return UserEntity(
        id = id,
        first_name = first_name,
        last_name = last_name,
        email = email,
        avatar = avatar
    )
}

fun UserEntity.toUser(): User {
    return User(
        id = id,
        first_name = first_name,
        last_name = last_name,
        email = email,
        avatar = avatar
    )
}