package com.example.retrofitandpictureloading.data

import com.example.retrofitandpictureloading.db.UserDao
import com.example.retrofitandpictureloading.db.UserEntity
import com.example.retrofitandpictureloading.model.ApiResponse
import com.example.retrofitandpictureloading.model.Name
import com.example.retrofitandpictureloading.model.Picture
import com.example.retrofitandpictureloading.model.User
import com.example.retrofitandpictureloading.network.RandomUsersApiService

interface RandomUsersRepository {
    suspend fun getRandomUsers(page: Int): ApiResponse
    suspend fun getUsersFromDatabase(): List<User>
}

class NetworkRandomUsersRepository(
    private val randomUsersApiService: RandomUsersApiService,
    private val userDao: UserDao
): RandomUsersRepository {
    override suspend fun getRandomUsers(page: Int): ApiResponse {
        val response = randomUsersApiService.getUsers(page)
        saveUsersInDatabase(response.result)
        return response
    }

    private suspend fun saveUsersInDatabase(users: List<User>) {
        val userEntities = users.map { user ->
            UserEntity(
                gender = user.gender,
                name = "${user.name.title} ${user.name.firstName} ${user.name.lastName}",
                picture = user.picture.large
            )
        }
        userDao.upsertUsers(userEntities)
    }

    override suspend fun getUsersFromDatabase(): List<User> {
        val userEntities = userDao.getAllUsers()
        return userEntities.map { entity ->
            val nameParts = entity.name.split(" ")
            User (
                gender = entity.gender,
                name = Name(nameParts[0], nameParts[1], nameParts[2]),
                picture = Picture(large = entity.picture)
            )
        }
    }
}