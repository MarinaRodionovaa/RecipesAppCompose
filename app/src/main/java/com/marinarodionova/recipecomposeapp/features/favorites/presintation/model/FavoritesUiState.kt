package com.marinarodionova.recipecomposeapp.features.favorites.presintation.model

import com.marinarodionova.recipecomposeapp.features.recipes.presentation.model.RecipeUiModel

data class FavoritesUiState(
    val recipesList: List<RecipeUiModel> = emptyList(),
    val error: String = ""
)