package com.marinarodionova.recipecomposeapp.features.recipes.presentation

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marinarodionova.recipecomposeapp.data.repository.RecipesRepositoryStub
import com.marinarodionova.recipecomposeapp.features.recipes.presentation.model.RecipesUiState
import com.marinarodionova.recipecomposeapp.features.recipes.presentation.model.toUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.net.URLDecoder

class RecipesViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {
    private val _uiState = MutableStateFlow(RecipesUiState())
    val uiState: StateFlow<RecipesUiState> = _uiState.asStateFlow()

    private val categoryTitle: String =
        savedStateHandle.get<String>("categoryTitle")
            ?: ""

    private val categoryImageUrl: String =
        URLDecoder.decode(
            savedStateHandle.get<String>("categoryImageUrl")
                ?: "",
            "UTF-8"
        )

    private val categoryId: Int =
        savedStateHandle.get<Int>("categoryId")
            ?: -1

    init {
        _uiState.update {
            it.copy(
                categoryTitle = categoryTitle,
                categoryImageUrl = categoryImageUrl
            )
        }

        loadRecipes(categoryId)
    }

    private fun loadRecipes(categoryId: Int) {
        viewModelScope.launch {
            try {
                _uiState.update { currentState ->
                    currentState.copy(
                        recipesList = RecipesRepositoryStub.getRecipesByCategoryId(categoryId)
                            .map { it.toUiModel() },
                    )
                }

            } catch (e: Exception) {
                Log.d("!!!!", "Ошибка загрузки данных! ${e.message}")
            }
        }
    }
}