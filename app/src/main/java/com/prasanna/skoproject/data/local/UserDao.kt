package com.prasanna.skoproject.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface UserDao {
    @Upsert
    suspend fun upsertAll(users: List<UserEntity>)

    @Query("SELECT * FROM userentity")
    fun pagingSource(): PagingSource<Int, UserEntity>

    @Query("DELETE FROM userentity")
    suspend fun clearAll()
}