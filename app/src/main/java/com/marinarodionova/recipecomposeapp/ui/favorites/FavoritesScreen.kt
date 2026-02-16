package com.marinarodionova.recipecomposeapp.ui.favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.marinarodionova.recipecomposeapp.R
import com.marinarodionova.recipecomposeapp.data.repository.RecipesRepositoryStub
import com.marinarodionova.recipecomposeapp.ui.core.EmptyPlaceholder
import com.marinarodionova.recipecomposeapp.ui.core.ScreenHeader
import com.marinarodionova.recipecomposeapp.ui.recipes.components.RecipeItem
import com.marinarodionova.recipecomposeapp.ui.recipes.model.toUiModel
import com.marinarodionova.recipecomposeapp.ui.theme.Dimens
import com.marinarodionova.recipecomposeapp.ui.theme.RecipeComposeAppTheme
import com.marinarodionova.recipecomposeapp.utils.FavoritePrefsManager

@Composable
fun FavoritesScreen(
    modifier: Modifier = Modifier, favoritePref: FavoritePrefsManager,
    onRecipeClick: (Int) -> Unit,
) {
    Column(modifier = modifier) {
        ScreenHeader(
            stringResource(R.string.title_favorite),
            imageResId = R.drawable.bcg_favorites
        )
        val favoriteIds = favoritePref.getAllFavorites()
        if (favoriteIds.isEmpty()) {
            EmptyPlaceholder(text = stringResource(R.string.information_message_favorite_list))
        } else {
            val recipes = favoriteIds.map { it.toInt() }
                .map { id -> RecipesRepositoryStub.getRecipesByRecipeId(id) }
                .map { it.toUiModel() }
            LazyVerticalGrid(
                columns = GridCells.Fixed(1),
                horizontalArrangement = Arrangement.spacedBy(Dimens.standardMargin),
                verticalArrangement = Arrangement.spacedBy(Dimens.standardMargin),
                contentPadding = PaddingValues(Dimens.standardMargin),
            ) {
                items(recipes, key = { it.id }) { recipe ->
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

@Preview()
@Composable
fun FavoritesScreenPreview() {
    RecipeComposeAppTheme {
        val context = LocalContext.current
        val favoritePrefsManager = FavoritePrefsManager(context = context)
        FavoritesScreen(favoritePref = favoritePrefsManager, onRecipeClick = {})
    }
}
