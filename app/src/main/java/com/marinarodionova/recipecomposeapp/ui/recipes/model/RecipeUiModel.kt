package com.marinarodionova.recipecomposeapp.ui.recipes.model

import androidx.compose.runtime.Immutable
import com.marinarodionova.recipecomposeapp.Constants
import com.marinarodionova.recipecomposeapp.data.model.RecipeDto

@Immutable
data class RecipeUiModel(
    val id: Int,
    val title: String,
    val ingredients: List<IngredientUiModel>,
    val imageUrl: String,
    val method: List<String>,
)

fun RecipeDto.toUiModel() = RecipeUiModel(
    id = id,
    title = title,
    method = method,
    ingredients = ingredients.map { it.toUiModel() },
    imageUrl = if (imageUrl.startsWith("http")) imageUrl else Constants.ASSETS_URI_PREFIX + imageUrl
)
