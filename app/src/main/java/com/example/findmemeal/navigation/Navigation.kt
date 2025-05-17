package com.example.findmemeal.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.common.navigation.NavigationSubGraphRoute

@Composable
fun RecipeNavigation(
    modifier: Modifier = Modifier,
    navigationSubGraph: NavigationSubGraph
){
    val navHostController = rememberNavController()
    NavHost(
        navController = navHostController,
        startDestination = NavigationSubGraphRoute.Search.route
    ){
        navigationSubGraph.searchFeatureApi.registerGraph(
            navGraphBuilder = this,
            navHostController = navHostController
        )
    }

}