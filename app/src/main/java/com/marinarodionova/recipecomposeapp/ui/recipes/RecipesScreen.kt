package com.marinarodionova.recipecomposeapp.ui.recipes

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
import com.marinarodionova.recipecomposeapp.data.repository.RecipesRepositoryStub
import com.marinarodionova.recipecomposeapp.data.repository.RecipesRepositoryStub.getRecipesByCategoryId
import com.marinarodionova.recipecomposeapp.ui.core.ScreenHeader
import com.marinarodionova.recipecomposeapp.ui.recipes.components.RecipeItem
import com.marinarodionova.recipecomposeapp.ui.recipes.model.RecipeUiModel
import com.marinarodionova.recipecomposeapp.ui.recipes.model.toUiModel
import com.marinarodionova.recipecomposeapp.ui.theme.Dimens
import com.marinarodionova.recipecomposeapp.ui.theme.RecipeComposeAppTheme
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.marinarodionova.recipecomposeapp.R
import com.marinarodionova.recipecomposeapp.ui.categories.model.toUiModel
import com.marinarodionova.recipecomposeapp.ui.core.EmptyPlaceholder

@Composable
fun RecipesScreen(modifier: Modifier = Modifier, categoryId: Int) {
    var recipes by remember { mutableStateOf<List<RecipeUiModel>>(emptyList()) }
    val category = RecipesRepositoryStub.getCategoryByCategoryId(categoryId).toUiModel()

    LaunchedEffect(category) {
        recipes = getRecipesByCategoryId(category.id).map { dto -> dto.toUiModel() }
    }

    Column(modifier = modifier) {
        ScreenHeader(
            title = category.title,
            imageUrl = category.imageUrl
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
                items(items = recipes) { recipe ->
                    RecipeItem(
                        imageUrl = recipe.imageUrl,
                        title = recipe.title,
                        onCLick = {}
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
        RecipesScreen(categoryId = 0)
    }
}