package com.prasanna.skoproject.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserEntity(
    val avatar: String,
    val email: String,
    val first_name: String,
    @PrimaryKey
    val id: Int,
    val last_name: String
)