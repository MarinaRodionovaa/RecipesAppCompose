package com.marinarodionova.recipecomposeapp.features.favorites.presintation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.marinarodionova.recipecomposeapp.data.FavoriteDataStoreManager
import com.marinarodionova.recipecomposeapp.data.repository.RecipesRepositoryStub
import com.marinarodionova.recipecomposeapp.features.recipes.presentation.model.RecipeUiModel
import com.marinarodionova.recipecomposeapp.features.recipes.presentation.model.toUiModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import androidx.lifecycle.viewModelScope
import com.marinarodionova.recipecomposeapp.features.favorites.presintation.model.FavoritesUiState
import kotlinx.coroutines.flow.SharingStarted

class FavoritesViewModel(
    application: Application,
) : AndroidViewModel(application) {
    private val favoriteDataStoreManager = FavoriteDataStoreManager(application)
    private val repository = RecipesRepositoryStub

    val uiState: StateFlow<FavoritesUiState> = favoriteDataStoreManager
        .getFavoriteIdsFlow()
        .map { ids ->
            FavoritesUiState(
                recipesList = loadFavoriteRecipes(ids),
                error = "",
            )
        }
        .catch { throwable ->
            emit(
                FavoritesUiState(
                    recipesList = emptyList(),
                    error = throwable.message ?: "Ошибка загрузки избранного",
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = FavoritesUiState(
                recipesList = emptyList(),
                error = "",
            )
        )

    private fun loadFavoriteRecipes(ids: Set<String>): List<RecipeUiModel> {
        return ids.mapNotNull { id ->
            runCatching {
                repository.getRecipesByRecipeId(id.toInt()).toUiModel()
            }.getOrNull()
        }
    }

}