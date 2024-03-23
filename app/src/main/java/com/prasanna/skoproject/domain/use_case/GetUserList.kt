package com.prasanna.skoproject.domain.use_case

import android.util.Log
import androidx.paging.PagingData
import androidx.paging.map
import com.prasanna.skoproject.data.mapper.toUser
import com.prasanna.skoproject.domain.model.User
import com.prasanna.skoproject.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map

class GetUserList(private val repository: UserRepository) {
    suspend operator fun invoke(): Flow<PagingData<User>> {

        return repository.getUsers().map { pagingData ->
            pagingData.map { it.toUser() }
        }
    }
}