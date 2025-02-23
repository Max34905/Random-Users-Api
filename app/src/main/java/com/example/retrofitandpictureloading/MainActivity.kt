package com.example.retrofitandpictureloading

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.retrofitandpictureloading.ui.screens.HomeScreenWithViewModel
import com.example.retrofitandpictureloading.ui.theme.RetrofitAndPictureLoadingTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RetrofitAndPictureLoadingTheme {
                Scaffold (
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    HomeScreenWithViewModel(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

