package com.prasanna.skoproject.data.remote

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.prasanna.skoproject.data.local.UserDatabase
import com.prasanna.skoproject.data.local.UserEntity
import com.prasanna.skoproject.data.mapper.toUserEntity
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class UserRemoteMediator(
    private val userDb: UserDatabase,
    private val userApi: UserApi
): RemoteMediator<Int, UserEntity>() {
    private val TAG:String = "USER REMOTE MEDIATOR"
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, UserEntity>
    ): MediatorResult {
        return try {
            val loadKey = when(loadType){
                LoadType.REFRESH -> {
                    Log.d(TAG, "Refreshing, hence setting LoadKey to 1")
                    1
                }
                LoadType.PREPEND -> {
                    Log.d(TAG, "End of pagination reached")
                    return MediatorResult.Success(
                    endOfPaginationReached = true)
                }
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if(lastItem == null){
                        Log.d(TAG, "Last item is NULL, hence setting LoadKey to 1")
                        1
                    }else{
                        Log.d(TAG, "Setting LoadKey to ${(lastItem.id / state.config.pageSize) + 1}")
                        (lastItem.id / state.config.pageSize) + 1
                    }
                }
            }

            val response = userApi.getUsers(page = loadKey)
            var userResponseDto:UserResponseDto? = UserResponseDto()
            var users:List<UserDto>? = null
            if(response.body() != null && response.code() == 200){
                userResponseDto = response.body()
            }
            users = userResponseDto?.users
            Log.d(TAG, "Users: $users")
            userDb.withTransaction{
                if(loadType == LoadType.REFRESH) {
                    userDb.dao.clearAll()
                }
                val userEntities = users?.map { it.toUserEntity() }
                userEntities?.let{
                    userDb.dao.upsertAll(it)
                }
            }

            MediatorResult.Success(
                endOfPaginationReached = users.isNullOrEmpty()
            )

        }catch (e: IOException){
            MediatorResult.Error(e)
        }catch (e: HttpException){
            MediatorResult.Error(e)
        }
    }
}