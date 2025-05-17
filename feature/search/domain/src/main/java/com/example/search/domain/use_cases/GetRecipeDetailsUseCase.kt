package com.example.search.domain.use_cases

import com.example.common.utils.NetworkResult
import com.example.search.domain.model.RecipeDetails
import com.example.search.domain.repository.RecipeSearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetRecipeDetailsUseCase @Inject constructor(private val recipeSearchRepository: RecipeSearchRepository) {

    operator fun invoke(id: String) = flow<NetworkResult<RecipeDetails>>{
        emit(NetworkResult.loading())
        val response = recipeSearchRepository.getRecipeDetails(id)
        if (response.isSuccess){
            emit(NetworkResult.success(response.getOrNull()))
        }else{
            emit(NetworkResult.error(response.exceptionOrNull()?.message))
        }
    }.catch {
        emit(NetworkResult.error(it.message.toString()))
    }.flowOn(Dispatchers.IO)

}