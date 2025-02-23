package com.example.retrofitandpictureloading.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val gender: String,
    val name: String,
    val picture: String
)
