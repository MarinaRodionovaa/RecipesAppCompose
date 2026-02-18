package com.marinarodionova.recipecomposeapp.ui.favorites

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.marinarodionova.recipecomposeapp.R
import com.marinarodionova.recipecomposeapp.data.FavoriteDataStoreManager
import com.marinarodionova.recipecomposeapp.data.repository.RecipesRepositoryStub
import com.marinarodionova.recipecomposeapp.ui.core.EmptyPlaceholder
import com.marinarodionova.recipecomposeapp.ui.core.ScreenHeader
import com.marinarodionova.recipecomposeapp.ui.recipes.components.RecipeItem
import com.marinarodionova.recipecomposeapp.ui.recipes.model.toUiModel
import com.marinarodionova.recipecomposeapp.ui.theme.Dimens
import com.marinarodionova.recipecomposeapp.ui.theme.RecipeComposeAppTheme
import kotlinx.coroutines.flow.map

@Composable
fun FavoritesScreen(
    modifier: Modifier = Modifier, favoritePref: FavoriteDataStoreManager,
    onRecipeClick: (Int) -> Unit, recipesRepository: RecipesRepositoryStub
) {
    val recipes by favoritePref.getFavoriteIdsFlow()
        .map { ids ->
            ids.mapNotNull { idStr ->
                val id = idStr.toIntOrNull() ?: return@mapNotNull null
                recipesRepository.getRecipesByRecipeId(id)
            }.map { it.toUiModel() }
        }
        .collectAsState(initial = emptyList())

    Column(modifier = modifier) {
        ScreenHeader(
            stringResource(R.string.title_favorite),
            imageResId = R.drawable.bcg_favorites
        )
        if (recipes.isEmpty()) {
            EmptyPlaceholder(text = stringResource(R.string.information_message_favorite_list))
        } else {
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

@Preview
@Composable
fun FavoritesScreenPreview() {
    RecipeComposeAppTheme {
        val context = LocalContext.current
        val favoritePrefs = FavoriteDataStoreManager(context = context)
        FavoritesScreen(
            favoritePref = favoritePrefs,
            onRecipeClick = {},
            recipesRepository = RecipesRepositoryStub
        )
    }
}
