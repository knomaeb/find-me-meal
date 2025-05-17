package com.example.search.ui.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.utils.NetworkResult
import com.example.common.utils.UiText
import com.example.search.domain.model.RecipeDetails
import com.example.search.domain.use_cases.GetRecipeDetailsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class RecipeDetailsViewModel @Inject constructor(
    private val getRecipeDetailsUseCase: GetRecipeDetailsUseCase
): ViewModel() {
        private val _uiState = MutableStateFlow(com.example.search.ui.screens.details.RecipeDetails.UiState())
    val uiState: StateFlow<com.example.search.ui.screens.details.RecipeDetails.UiState> get() = _uiState.asStateFlow()

    fun onEvent(event: com.example.search.ui.screens.details.RecipeDetails.Event) {
        when (event){
            is com.example.search.ui.screens.details.RecipeDetails.Event.FetchRecipeDetails -> {
                recipeDetails(event.id)
            }
        }
    }

    private fun recipeDetails(id: String) = getRecipeDetailsUseCase.invoke(id)
        .onEach { result->
            when(result){
                is NetworkResult.loading -> {
                    _uiState.value = _uiState.value.copy(isLoading = true)
                }
                is NetworkResult.error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = UiText.RemoteString(result.message.toString())
                    )
                }
                is NetworkResult.success -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        data = result.data
                    )
                }
            }
        }.launchIn(viewModelScope)

    }

object RecipeDetails{
    data class UiState(
        val isLoading: Boolean = false,
        val data: RecipeDetails? = null,
        val error: UiText = UiText.Idle
    )

    sealed interface Navigation {}

    sealed interface Event {
        data class FetchRecipeDetails(val id: String) : Event
    }
}