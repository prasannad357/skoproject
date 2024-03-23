package com.prasanna.skoproject.di

import android.app.Application
import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.prasanna.skoproject.R
import com.prasanna.skoproject.data.local.UserDatabase
import com.prasanna.skoproject.data.local.UserEntity
import com.prasanna.skoproject.data.remote.UserApi
import com.prasanna.skoproject.data.remote.UserRemoteMediator
import com.prasanna.skoproject.data.repository.UserRepositoryImpl
import com.prasanna.skoproject.domain.repository.UserRepository
import com.prasanna.skoproject.domain.use_case.GetUserList
import com.prasanna.skoproject.domain.use_case.UsersUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun providesRetrofitInstance( app:Application): Retrofit {
        return Retrofit.Builder().baseUrl(app.getString(R.string.base_url)).addConverterFactory(
            GsonConverterFactory.create()
        ).build()
    }


    @Provides
    @Singleton
    fun providesUsersApi(retrofit: Retrofit): UserApi {
        return retrofit.create(UserApi::class.java)
    }

    @Provides
    @Singleton
    fun providesUserDatabase(@ApplicationContext context:Context):UserDatabase{
        return Room.databaseBuilder(context,
            UserDatabase::class.java,
            "users.db").build()
    }

    @OptIn(ExperimentalPagingApi::class)
    @Provides
    @Singleton
    fun providesUserPager(userDb:UserDatabase, userApi: UserApi): Pager<Int, UserEntity>{
        return Pager(
            config = PagingConfig(pageSize = 6),
            remoteMediator = UserRemoteMediator(
                userDb, userApi
            ),
            pagingSourceFactory = {
                userDb.dao.pagingSource()
            }
        )
    }



    @Provides
    @Singleton
    fun providesRepository(pager: Pager<Int, UserEntity>):UserRepository{
        return UserRepositoryImpl(pager)
    }


    @Provides
    @Singleton
    fun providesUseCases(repository: UserRepository):UsersUseCases{
        return UsersUseCases( getUserList = GetUserList(repository))
    }

}