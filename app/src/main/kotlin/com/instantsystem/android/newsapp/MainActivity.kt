package com.instantsystem.android.newsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.instantsystem.android.core.designsystem.theme.InstantSystemNewsTheme
import com.instantsystem.android.feature.news.ui.NewsHomeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InstantSystemNewsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NewsHomeScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}