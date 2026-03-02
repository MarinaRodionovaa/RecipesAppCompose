package com.marinarodionova.recipecomposeapp.features.recipes.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.marinarodionova.recipecomposeapp.data.repository.RecipesRepositoryStub.getRecipesByCategoryId
import com.marinarodionova.recipecomposeapp.core.ui.ScreenHeader
import com.marinarodionova.recipecomposeapp.features.recipes.presentation.model.RecipeUiModel
import com.marinarodionova.recipecomposeapp.core.ui.theme.Dimens
import com.marinarodionova.recipecomposeapp.core.ui.theme.RecipeComposeAppTheme
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.marinarodionova.recipecomposeapp.R
import com.marinarodionova.recipecomposeapp.core.ui.EmptyPlaceholder
import com.marinarodionova.recipecomposeapp.features.recipes.presentation.model.toUiModel

@Composable
fun RecipesScreen(
    modifier: Modifier = Modifier,
    categoryId: Int,
    categoryTitle: String,
    categoryImageUrl: String,
    onRecipeClick: (Int) -> Unit,
) {
    var recipes by remember { mutableStateOf<List<RecipeUiModel>>(emptyList()) }

    LaunchedEffect(categoryId) {
        recipes = getRecipesByCategoryId(categoryId).map { dto -> dto.toUiModel() }
    }

    Column(modifier = modifier) {
        ScreenHeader(
            title = categoryTitle,
            imageUrl = categoryImageUrl
        )
        if (recipes.isEmpty()) {
            EmptyPlaceholder(text = stringResource(R.string.information_message_recipe_list))
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


@Preview(
    name = "LightTheme",
    showBackground = true
)
@Composable
fun CategoriesScreenPreview() {
    RecipeComposeAppTheme {
        RecipesScreen(
            categoryId = 0,
            onRecipeClick = { _ -> },
            categoryImageUrl = "",
            categoryTitle = ""
        )
    }
}