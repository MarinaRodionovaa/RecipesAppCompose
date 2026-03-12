package com.marinarodionova.recipecomposeapp.features.details.presentation.model

import com.marinarodionova.recipecomposeapp.features.details.ui.PINCH_TEXT

data class RecipeDetailsUiState(
    val recipeId: Int = -1,
    val recipeName: String = "",
    val method: List<String> = emptyList(),
    val ingredientList: List<IngredientUiModel> = emptyList(),
    val portionCount: Int = 1,
    val isFavorite: Boolean = false,
    val imageUrl: String = "",
    val error: String = ""
) {
    val recalculatedIngredients: List<IngredientUiModel>
        get() = ingredientList.map { ingredient ->
            val value = ingredient.quantity.toDoubleOrNull()
            if (value == null) {
                ingredient
            } else {
                ingredient.copy(
                    quantity = formatSmartAmount((value * portionCount).toString())
                )
            }
        }

    private fun formatSmartAmount(raw: String): String {
        val value = raw.toFloatOrNull() ?: return raw

        val whole = value.toInt()
        val fraction = value - whole

        val fractionText = when {
            fraction >= 0.75f -> "3/4"
            fraction >= 0.5f -> "1/2"
            fraction >= 0.25f -> "1/4"
            else -> ""
        }

        return when {
            whole == 0 && fraction < 0.25f ->
                PINCH_TEXT

            whole > 0 && fractionText.isNotEmpty() ->
                "$whole $fractionText"

            whole > 0 ->
                whole.toString()

            else ->
                fractionText
        }
    }
}