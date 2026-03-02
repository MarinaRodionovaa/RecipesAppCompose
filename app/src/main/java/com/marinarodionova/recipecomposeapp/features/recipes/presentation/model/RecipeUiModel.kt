package com.marinarodionova.recipecomposeapp.features.recipes.presentation.model

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import com.marinarodionova.recipecomposeapp.core.utils.Constants
import com.marinarodionova.recipecomposeapp.data.model.RecipeDto
import com.marinarodionova.recipecomposeapp.features.details.presentation.model.IngredientUiModel
import com.marinarodionova.recipecomposeapp.features.details.presentation.model.toUiModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.parcelize.Parcelize

@Immutable
@Parcelize
data class RecipeUiModel(
    val id: Int,
    val title: String,
    val ingredients: ImmutableList<IngredientUiModel>,
    val imageUrl: String,
    val method: ImmutableList<String>,
) : Parcelable

fun RecipeDto.toUiModel() = RecipeUiModel(
    id = id,
    title = title,
    method = method.toImmutableList(),
    ingredients = ingredients.map { it.toUiModel() }.toImmutableList(),
    imageUrl = if (imageUrl.startsWith("http")) imageUrl else Constants.ASSETS_URI_PREFIX + imageUrl
)
