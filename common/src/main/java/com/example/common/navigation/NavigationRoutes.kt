package com.example.common.navigation

sealed class NavigationRoute(val route: String) {
    data object RecipeList : NavigationRoute("/recipe_List")
    data object RecipeDetail : NavigationRoute("/recipe_Details/{id}"){
        fun sendId(id: String) = "/recipe_Details/$id"
    }
}

sealed class NavigationSubGraphRoute(val route: String){
    data object Search : NavigationSubGraphRoute("/search")
}