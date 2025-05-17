package com.example.search.data.model

data class RecipeResponse(
    val meals: List<RecipeDTO>
)
data class RecipeDetailsResponse(
    val meals: List<RecipeDTO>? = null
)

