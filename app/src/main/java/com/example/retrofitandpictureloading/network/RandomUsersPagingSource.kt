package com.example.retrofitandpictureloading.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.retrofitandpictureloading.data.RandomUsersRepository
import com.example.retrofitandpictureloading.model.User
import java.net.UnknownHostException

class RandomUsersPagingSource(
    private val randomUsersRepository: RandomUsersRepository
) : PagingSource<Int, User>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        val page = params.key ?: 1
        return try {
            val response = randomUsersRepository.getRandomUsers(page)
            LoadResult.Page(
                data = response.result,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.result.isEmpty()) null else page + 1
            )
        } catch (e: UnknownHostException) {
            val usersFromDb = randomUsersRepository.getUsersFromDatabase()
            if (!usersFromDb.isEmpty()) {
                LoadResult.Page(
                    data = usersFromDb,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (usersFromDb.isEmpty()) null else page + 1
                )
            } else {
                LoadResult.Error(e)
            }
        }
    }

    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1) ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}