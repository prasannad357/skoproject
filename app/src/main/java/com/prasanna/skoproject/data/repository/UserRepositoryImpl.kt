package com.prasanna.skoproject.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.prasanna.skoproject.data.local.UserEntity
import com.prasanna.skoproject.domain.model.User
import com.prasanna.skoproject.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val pager: Pager<Int, UserEntity>):UserRepository {

    override suspend fun getUsers(): Flow<PagingData<UserEntity>> {
        return pager.flow
    }
}