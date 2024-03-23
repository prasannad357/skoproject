package com.prasanna.skoproject.domain.repository

import androidx.paging.PagingData
import com.prasanna.skoproject.data.local.UserEntity
import com.prasanna.skoproject.domain.model.User
import kotlinx.coroutines.flow.Flow


interface UserRepository {
    suspend fun getUsers(): Flow<PagingData<UserEntity>>
}