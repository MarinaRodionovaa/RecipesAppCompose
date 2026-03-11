package com.marinarodionova.recipecomposeapp.features.details.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.marinarodionova.recipecomposeapp.data.FavoriteDataStoreManager
import com.marinarodionova.recipecomposeapp.data.repository.RecipesRepositoryStub
import com.marinarodionova.recipecomposeapp.features.details.presentation.model.RecipeDetailsUiState
import com.marinarodionova.recipecomposeapp.features.recipes.presentation.model.toUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RecipeDetailsViewModel(
    application: Application,
    savedStateHandle: SavedStateHandle,
) : AndroidViewModel(application) {
    private val favoriteDataStoreManager = FavoriteDataStoreManager(application)

    private val _uiState = MutableStateFlow(RecipeDetailsUiState())
    val uiState: StateFlow<RecipeDetailsUiState> = _uiState.asStateFlow()

    private val recipeId: Int =
        savedStateHandle.get<Int>("recipeId")
            ?: -1

    init {
        loadRecipeDetails(recipeId)
        observeFavoriteState()
    }

    private fun loadRecipeDetails(recipeId: Int) {
        viewModelScope.launch {
            try {
                val recipe = RecipesRepositoryStub.getRecipesByRecipeId(recipeId).toUiModel()
                _uiState.update { currentState ->
                    currentState.copy(
                        recipeId = recipeId,
                        recipeName = recipe.title,
                        imageUrl = recipe.imageUrl,
                        method = recipe.method,
                        ingredientList = recipe.ingredients,
                    )
                }

            } catch (e: Exception) {
                _uiState.update { currentState ->
                    currentState.copy(
                        error = "Oшибка загрузки данных!"
                    )
                }
            }
        }
    }

    private fun observeFavoriteState() {
        viewModelScope.launch {
            try {
                favoriteDataStoreManager.isFavoriteFlow(recipeId).collect { isFavorite ->
                    _uiState.update { currentState ->
                        currentState.copy(isFavorite = isFavorite)
                    }
                }
            } catch (e: Exception) {
                _uiState.update { currentState ->
                    currentState.copy(
                        error = "Oшибка загрузки данных!"
                    )
                }
            }
        }
    }

    fun updatePortionCount(portionsCount: Int) {
        viewModelScope.launch {
            try {
                _uiState.update { currentState ->
                    currentState.copy(
                        portionCount = portionsCount
                    )
                }

            } catch (e: Exception) {
                _uiState.update { currentState ->
                    currentState.copy(
                        error = "Oшибка загрузки данных!"
                    )
                }
            }
        }
    }

    fun toggleFavorite() {
        viewModelScope.launch {
            val isFavorite = favoriteDataStoreManager.isFavoriteFlow(recipeId).first()

            if (isFavorite) {
                favoriteDataStoreManager.removeFavorite(recipeId)
            } else {
                favoriteDataStoreManager.addFavorite(recipeId)
            }
        }
    }
}