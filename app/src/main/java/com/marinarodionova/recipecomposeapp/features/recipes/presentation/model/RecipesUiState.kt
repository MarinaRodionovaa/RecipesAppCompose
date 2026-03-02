package com.marinarodionova.recipecomposeapp.features.recipes.presentation.model

data class RecipesUiState(
    val recipesList: List<RecipeUiModel> = emptyList(),
    val categoryTitle: String = "",
    val categoryImageUrl: String = "",
)