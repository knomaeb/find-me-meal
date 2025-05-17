package com.example.search.domain.use_cases

import com.example.common.utils.NetworkResult
import com.example.search.domain.model.Recipe
import com.example.search.domain.repository.RecipeSearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetAllRecipeUseCase @Inject constructor(private val recipeSearchRepository: RecipeSearchRepository) {

    operator fun invoke(searchQuery: String) = flow<NetworkResult<List<Recipe>>>{
        emit(NetworkResult.loading())
        val response = recipeSearchRepository.getRecipes(searchQuery)
        if (response.isSuccess){
            emit(NetworkResult.success(response.getOrNull()))
        }else{
            emit(NetworkResult.error(response.exceptionOrNull()?.message))
        }
    }.catch {
        emit(NetworkResult.error(it.message.toString()))
    }.flowOn(Dispatchers.IO)
}