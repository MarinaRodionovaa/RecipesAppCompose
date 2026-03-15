package com.marinarodionova.recipecomposeapp.features.details.ui

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import com.marinarodionova.recipecomposeapp.core.ui.theme.Dimens
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.marinarodionova.recipecomposeapp.R
import com.marinarodionova.recipecomposeapp.features.details.presentation.model.IngredientUiModel
import kotlin.math.roundToInt
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.marinarodionova.recipecomposeapp.core.ui.EmptyPlaceholder
import com.marinarodionova.recipecomposeapp.core.ui.theme.RecipeComposeAppTheme
import com.marinarodionova.recipecomposeapp.features.details.presentation.RecipeDetailsViewModel

private const val MIN_PORTIONS = 1
private const val MAX_PORTIONS = 10
private const val STEPS = 10
const val PINCH_TEXT = "щепотка"

@Composable
fun RecipeDetailsScreen(
    viewModel: RecipeDetailsViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val scaledIngredients = state.recalculatedIngredients
    val context = LocalContext.current

    LazyColumn {
        item {
            RecipeHeader(
                recipeImageUrl = state.imageUrl,
                recipeId = state.recipeId,
                recipeTitle = state.recipeName,
                context = context,
                modifier = Modifier,
                isFavorite = state.isFavorite,
                onFavoriteToggle = {
                    viewModel.toggleFavorite()
                }
            )
        }
        if (state.recipeId == -1) {
            item { EmptyPlaceholder(text = state.error) }
        } else {
            item {
                PortionsSlider(
                    currentPortions = state.portionCount,
                    onPortionsChange = { newValue ->
                        viewModel.updatePortionCount(newValue)
                    }
                )
            }
            item { IngredientsList(scaledIngredients) }
            item { InstructionsList(state.method) }
        }

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

        val quantityText = ingredient.quantity
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
    RecipeComposeAppTheme { RecipeDetailsScreen() }
}
