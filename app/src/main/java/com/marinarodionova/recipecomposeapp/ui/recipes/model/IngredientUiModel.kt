package com.marinarodionova.recipecomposeapp.ui.recipes.model

import androidx.compose.runtime.Immutable
import com.marinarodionova.recipecomposeapp.data.model.IngredientDto

@Immutable
data class IngredientUiModel(
    val quantity: String,
    val unitOfMeasure: String,
    val description: String,
)

fun IngredientDto.toUiModel() = IngredientUiModel(
    quantity = quantity,
    unitOfMeasure = unitOfMeasure,
    description = description,
)
