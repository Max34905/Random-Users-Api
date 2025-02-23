package com.example.retrofitandpictureloading.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.retrofitandpictureloading.R
import com.example.retrofitandpictureloading.model.Name
import com.example.retrofitandpictureloading.model.Picture
import com.example.retrofitandpictureloading.model.User
import kotlinx.coroutines.flow.flowOf

@Composable
fun HomeScreenWithViewModel(
    viewModel: HomeScreenViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val lazyPagingItems = viewModel.homeScreenUiState.collectAsLazyPagingItems()
    HomeScreen(lazyPagingItems)
}

@Composable
fun HomeScreen(
    users: LazyPagingItems<User>,
    modifier: Modifier = Modifier
) {
    LazyColumn {
        items(users.itemCount) { index ->
            users[index]?.let { user ->
                ListItemCard(user)
            }
        }
        users.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item { LoadingScreen(modifier = Modifier.fillMaxSize()) }
                }
                loadState.append is LoadState.Loading -> {
                    item { LoadingScreen(modifier = Modifier.fillMaxSize()) }
                }
                loadState.refresh is LoadState.Error -> {
                    item { ErrorScreen(modifier = Modifier.fillMaxSize()) }
                }
            }
        }
    }
}

@Composable
fun ListItemCard(user: User, modifier: Modifier = Modifier) {
    Card (
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column (
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(user.picture.large)
                    .crossfade(true)
                    .build(),
                contentDescription = stringResource(R.string.user_photo_description),
                modifier = modifier
                    .padding(top = 8.dp)
                    .height(150.dp)
                    .aspectRatio(1f)
                    .fillMaxHeight()
            )
            Text(text = "${user.name.title} ${user.name.firstName} ${user.name.lastName}")
            Text(text = user.gender)
        }
    }
}

@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.wrapContentSize(Alignment.Center)
    ) {
        Text(text = stringResource(R.string.loading_failed))
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.wrapContentSize(Alignment.Center)
    ) {
        Text(text = stringResource(R.string.loading))
    }
}

@Composable
@Preview(showSystemUi = true)
private fun HomeScreenPreview() {
    val users = listOf(
        User(gender = "male", name = Name("Mr", "Mark", "Brown"), picture = Picture()),
        User(gender = "female", name = Name("Ms", "Lisa",  "Renta"), picture = Picture()),
    )
    val pagingItems = flowOf(PagingData.from(users)).collectAsLazyPagingItems()
    HomeScreen(pagingItems)
}