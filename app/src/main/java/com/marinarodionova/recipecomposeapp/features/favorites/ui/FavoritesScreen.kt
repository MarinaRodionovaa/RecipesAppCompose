package com.marinarodionova.recipecomposeapp.features.favorites.ui

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.marinarodionova.recipecomposeapp.R
import com.marinarodionova.recipecomposeapp.core.ui.EmptyPlaceholder
import com.marinarodionova.recipecomposeapp.core.ui.ScreenHeader
import com.marinarodionova.recipecomposeapp.features.recipes.ui.RecipeItem
import com.marinarodionova.recipecomposeapp.core.ui.theme.Dimens
import com.marinarodionova.recipecomposeapp.core.ui.theme.RecipeComposeAppTheme
import com.marinarodionova.recipecomposeapp.features.favorites.presintation.FavoritesViewModel

@Composable
fun FavoritesScreen(
    modifier: Modifier = Modifier,
    onRecipeClick: (Int) -> Unit,
    viewModel: FavoritesViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsState()
    Column(modifier = modifier) {
        ScreenHeader(
            stringResource(R.string.title_favorite),
            imageResId = R.drawable.bcg_favorites
        )
        if (state.recipesList.isEmpty() && state.error.isEmpty()) {
            EmptyPlaceholder(text = stringResource(R.string.information_message_favorite_list))
        } else if (state.recipesList.isEmpty() && state.error.isNotEmpty()) {
            EmptyPlaceholder(text = state.error)
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

@Preview
@Composable
fun FavoritesScreenPreview() {
    RecipeComposeAppTheme {
        FavoritesScreen(
            onRecipeClick = {},
        )
    }
}
