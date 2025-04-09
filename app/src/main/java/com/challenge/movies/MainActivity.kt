package com.challenge.movies

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.challenge.movies.navigation.SetupNavGraph
import com.challenge.movies.ui.theme.PopularMoviesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PopularMoviesTheme {
                val navController = rememberNavController()
                SetupNavGraph(navController)
            }
        }
    }
}