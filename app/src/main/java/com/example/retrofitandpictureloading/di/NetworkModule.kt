package com.example.retrofitandpictureloading.di

import android.content.Context
import androidx.room.Room
import com.example.retrofitandpictureloading.data.NetworkRandomUsersRepository
import com.example.retrofitandpictureloading.data.RandomUsersRepository
import com.example.retrofitandpictureloading.db.AppDatabase
import com.example.retrofitandpictureloading.db.UserDao
import com.example.retrofitandpictureloading.network.RandomUsersApiService
import com.example.retrofitandpictureloading.network.RetrofitClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRandomUsersApiService(): RandomUsersApiService {
        return RetrofitClient.instance
    }

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "users.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideUserDao(
        db: AppDatabase
    ): UserDao {
        return db.userDao
    }

    @Provides
    @Singleton
    fun provideRandomUsersRepository(
        randomUsersApiService: RandomUsersApiService,
        userDao: UserDao
    ): RandomUsersRepository {
        return NetworkRandomUsersRepository(randomUsersApiService, userDao)
    }
}