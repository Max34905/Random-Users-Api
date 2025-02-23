package com.example.retrofitandpictureloading.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [UserEntity::class],
    version = 1
)
abstract class AppDatabase: RoomDatabase() {
    abstract val userDao: UserDao
}