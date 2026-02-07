package com.marinarodionova.recipecomposeapp.ui.recipes.model

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import com.marinarodionova.recipecomposeapp.data.model.IngredientDto
import kotlinx.parcelize.Parcelize

@Immutable
@Parcelize
data class IngredientUiModel(
    val quantity: String,
    val unitOfMeasure: String,
    val description: String,
) : Parcelable

fun IngredientDto.toUiModel() = IngredientUiModel(
    quantity = quantity,
    unitOfMeasure = unitOfMeasure,
    description = description,
)
