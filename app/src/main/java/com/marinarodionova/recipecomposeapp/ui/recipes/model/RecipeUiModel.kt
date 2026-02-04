package com.marinarodionova.recipecomposeapp.ui.recipes.model

import androidx.compose.runtime.Immutable
import com.marinarodionova.recipecomposeapp.Constants
import com.marinarodionova.recipecomposeapp.data.model.RecipeDto
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Immutable
data class RecipeUiModel(
    val id: Int,
    val title: String,
    val ingredients: ImmutableList<IngredientUiModel>,
    val imageUrl: String,
    val method: ImmutableList<String>,
)

fun RecipeDto.toUiModel() = RecipeUiModel(
    id = id,
    title = title,
    method = method.toImmutableList(),
    ingredients = ingredients.map { it.toUiModel() }.toImmutableList(),
    imageUrl = if (imageUrl.startsWith("http")) imageUrl else Constants.ASSETS_URI_PREFIX + imageUrl
)
