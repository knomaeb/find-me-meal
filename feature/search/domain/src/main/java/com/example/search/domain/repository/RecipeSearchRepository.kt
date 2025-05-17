package com.example.search.domain.repository

import com.example.search.domain.model.Recipe
import com.example.search.domain.model.RecipeDetails

interface RecipeSearchRepository {

    suspend fun getRecipes(s: String): Result<List<Recipe>>

    suspend fun getRecipeDetails(id : String): Result<RecipeDetails>
}