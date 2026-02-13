package com.marinarodionova.recipecomposeapp.ui.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import com.marinarodionova.recipecomposeapp.data.repository.RecipesRepositoryStub
import com.marinarodionova.recipecomposeapp.ui.recipes.model.toUiModel
import com.marinarodionova.recipecomposeapp.ui.theme.Dimens
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.marinarodionova.recipecomposeapp.R
import com.marinarodionova.recipecomposeapp.ui.recipes.model.IngredientUiModel
import kotlin.math.roundToInt
import androidx.compose.ui.platform.LocalContext

private const val MIN_PORTIONS = 1
private const val MAX_PORTIONS = 10
private const val STEPS = 10
private const val DEFAULT_PORTIONS = 1
private const val PINCH_TEXT = "щепотка"

@Composable
fun RecipeDetailsScreen(recipeId: Int) {
    val portions = DEFAULT_PORTIONS
    val recipe = RecipesRepositoryStub.getRecipesByRecipeId(recipeId).toUiModel()

    var currentPortions by remember { mutableIntStateOf(portions) }

    val scaledIngredients = remember(currentPortions) {
        recipe.ingredients.map { ingredient ->
            val value = ingredient.quantity.toDoubleOrNull()

            if (value == null) {
                ingredient
            } else {
                ingredient.copy(
                    quantity = (value * currentPortions).toString()
                )
            }
        }
    }
    val context = LocalContext.current

    LazyColumn {
        item {
            RecipeHeader(
                recipe = recipe,
                context = context,
                modifier = Modifier
            )
        }
        item {
            PortionsSlider(
                currentPortions = currentPortions,
                onPortionsChange = { newValue ->
                    currentPortions = newValue
                }
            )
        }
        item { IngredientsList(scaledIngredients) }
        item { InstructionsList(recipe.method) }
    }
}

@Composable
fun IngredientsList(
    scaledIngredients: List<IngredientUiModel>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(Dimens.standardPadding)
            .clip(RoundedCornerShape(Dimens.radiusSmall))
            .background(MaterialTheme.colorScheme.surface)
            .padding(Dimens.mediumPadding)
    ) {
        scaledIngredients.forEachIndexed { index, ingredient ->
            IngredientItem(
                ingredient = ingredient,
                modifier = Modifier
            )

            if (index < scaledIngredients.lastIndex) {
                HorizontalDivider(
                    thickness = Dimens.dividerThickness,
                    color = MaterialTheme.colorScheme.outlineVariant,
                    modifier = Modifier.padding(vertical = Dimens.mediumPadding)
                )
            }
        }
    }
}

@Composable
fun IngredientItem(
    ingredient: IngredientUiModel,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically

    ) {
        Text(
            text = ingredient.description.uppercase(),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.weight(1f)
        )

        val quantityText = formatSmartAmount(ingredient.quantity)
        val unitText = ingredient.unitOfMeasure

        Text(
            text = "$quantityText $unitText".uppercase(),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.secondary,
            textAlign = TextAlign.End,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PortionsSlider(
    currentPortions: Int,
    onPortionsChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Text(
        text = stringResource(R.string.title_ingredient).uppercase(),
        style = MaterialTheme.typography.displayLarge,
        color = MaterialTheme.colorScheme.primary,
        modifier = modifier.padding(start = Dimens.standardPadding, top = Dimens.standardPadding)
    )
    Text(
        text = ("${stringResource(R.string.title_portion)} $currentPortions"),
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.secondary,
        modifier = Modifier.padding(start = Dimens.standardPadding, top = Dimens.mediumMargin)
    )
    Slider(
        value = currentPortions.toFloat(),
        onValueChange = { onPortionsChange(it.roundToInt()) },
        valueRange = MIN_PORTIONS.toFloat()..MAX_PORTIONS.toFloat(),
        steps = STEPS,
        track = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimens.seekbarHeight)
                    .background(
                        color = MaterialTheme.colorScheme.tertiaryContainer,
                        shape = RoundedCornerShape(Dimens.radiusLittle)
                    )
            )
        },
        thumb = {
            Box(
                modifier = Modifier
                    .height(Dimens.thumbHeight)
                    .width(Dimens.thumbWidth)
                    .clip(RoundedCornerShape(Dimens.radiusTiny))
                    .background(MaterialTheme.colorScheme.tertiary)
            )
        },
        modifier = Modifier.padding(
            horizontal = Dimens.standardPadding,
        )
    )
}

@Composable
fun InstructionsList(method: List<String>) {
    Text(
        text = stringResource((R.string.title_method)).uppercase(),
        style = MaterialTheme.typography.displayLarge,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(start = Dimens.standardPadding, top = Dimens.standardPadding)
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimens.standardPadding)
            .clip(RoundedCornerShape(Dimens.radiusSmall))
            .background(MaterialTheme.colorScheme.surface)
            .padding(Dimens.mediumPadding)
    ) {

        method.forEachIndexed { index, ingredient ->
            InstructionItem(method = ingredient)

            if (index < method.lastIndex) {
                HorizontalDivider(
                    thickness = Dimens.dividerThickness,
                    color = MaterialTheme.colorScheme.outlineVariant,
                    modifier = Modifier.padding(vertical = Dimens.mediumPadding)
                )
            }
        }
    }
}

fun formatSmartAmount(raw: String): String {
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

@Composable
fun InstructionItem(method: String, modifier: Modifier = Modifier) {

    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically

    ) {
        Text(
            text = method,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.weight(1f)
        )

    }

}

@Preview(
    name = "LightTheme",
    showBackground = true
)
@Composable
fun RecipeDetailsScreenPreview() {
    RecipeDetailsScreen(recipeId = 0)
}
