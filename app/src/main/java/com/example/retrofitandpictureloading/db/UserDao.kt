package com.example.retrofitandpictureloading.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface UserDao {
    @Upsert
    suspend fun upsertUsers(users: List<UserEntity>)

    @Query("SELECT * FROM users")
    suspend fun getAllUsers(): List<UserEntity>
}