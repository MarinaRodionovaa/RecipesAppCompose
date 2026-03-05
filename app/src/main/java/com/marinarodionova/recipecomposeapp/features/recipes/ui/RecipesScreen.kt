package com.marinarodionova.recipecomposeapp.features.recipes.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.marinarodionova.recipecomposeapp.core.ui.ScreenHeader
import com.marinarodionova.recipecomposeapp.core.ui.theme.Dimens
import com.marinarodionova.recipecomposeapp.core.ui.theme.RecipeComposeAppTheme
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.marinarodionova.recipecomposeapp.R
import com.marinarodionova.recipecomposeapp.core.ui.EmptyPlaceholder
import com.marinarodionova.recipecomposeapp.features.recipes.presentation.RecipesViewModel

@Composable
fun RecipesScreen(
    modifier: Modifier = Modifier,
    onRecipeClick: (Int) -> Unit,
    viewModel: RecipesViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Column(modifier = modifier) {
        ScreenHeader(
            title = state.categoryTitle,
            imageUrl = state.categoryImageUrl
        )

        if (state.recipesList.isEmpty()) {
            EmptyPlaceholder(
                text = state.error.ifEmpty { stringResource(R.string.information_message_recipe_list) }
            )
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(1),
                horizontalArrangement = Arrangement.spacedBy(Dimens.standardMargin),
                verticalArrangement = Arrangement.spacedBy(Dimens.standardMargin),
                contentPadding = PaddingValues(Dimens.standardMargin),
            ) {
                items(state.recipesList, key = { it.id }) { recipe ->
                    RecipeItem(
                        imageUrl = recipe.imageUrl,
                        title = recipe.title,
                        onCLick = { onRecipeClick(recipe.id) }
                    )
                }
            }
        }
    }
}

@Preview(
    name = "LightTheme",
    showBackground = true
)
@Composable
fun CategoriesScreenPreview() {
    RecipeComposeAppTheme {
        RecipesScreen(
            onRecipeClick = { _ -> },
        )
    }
}