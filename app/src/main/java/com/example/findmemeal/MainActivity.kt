package com.example.findmemeal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.findmemeal.navigation.NavigationSubGraph
import com.example.findmemeal.navigation.RecipeNavigation
import com.example.findmemeal.ui.theme.FindMeMealTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigationSubGraph: NavigationSubGraph

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FindMeMealTheme {
                Surface(
                    modifier = Modifier.safeContentPadding()
                ) {
                    RecipeNavigation(navigationSubGraph = navigationSubGraph)
                }
                Scaffold {  }
            }
        }
    }
}