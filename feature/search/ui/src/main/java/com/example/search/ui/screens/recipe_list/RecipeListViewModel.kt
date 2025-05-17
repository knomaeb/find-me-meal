package com.example.search.ui.screens.recipe_list

import android.net.Network
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.utils.NetworkResult
import com.example.common.utils.UiText
import com.example.search.domain.model.Recipe
import com.example.search.domain.use_cases.GetAllRecipeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    private val getAllRecipeUseCase: GetAllRecipeUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(RecipeList.UiState())
    val uiState: StateFlow<RecipeList.UiState> = _uiState.asStateFlow()

    fun onEvent(event: RecipeList.Event) {
        when (event) {
            is RecipeList.Event.SearchRecipe -> {
                search(event.searchQuery)
            }
//            is RecipeList.Event.RecipeClicked -> {
//                viewModelScope.launch {
//                    _uiState.value = _uiState.value.copy(navigation = RecipeList.Navigation.GoToRecipeDetails(event.id))
//                }
//            }
        }
    }

    private fun search(q: String) = getAllRecipeUseCase.invoke(q)
            .onEach { result ->
                when (result) {
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

object RecipeList {

    data class UiState(
        val isLoading: Boolean = false,
        val data: List<Recipe>? = null,
        val error: UiText = UiText.Idle
    )

    sealed interface Navigation{
        data class GoToRecipeDetails(val id: String) : Navigation
    }

    sealed interface Event {
        data class SearchRecipe(val searchQuery: String) : Event
//        data class RecipeClicked(val id: String) : Event
    }

}