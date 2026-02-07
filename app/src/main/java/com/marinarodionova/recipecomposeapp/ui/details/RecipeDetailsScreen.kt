package com.marinarodionova.recipecomposeapp.ui.details

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.marinarodionova.recipecomposeapp.data.repository.RecipesRepositoryStub
import com.marinarodionova.recipecomposeapp.ui.core.ScreenHeader
import com.marinarodionova.recipecomposeapp.ui.recipes.model.RecipeUiModel
import com.marinarodionova.recipecomposeapp.ui.recipes.model.toUiModel

@Composable
fun RecipeDetailsScreen(modifier: Modifier = Modifier, recipe: RecipeUiModel) {
    Column(modifier = modifier) {
        ScreenHeader(
            title = recipe.title,
            imageUrl = recipe.imageUrl
        )
    }
}


@Preview(
    name = "LightTheme",
    showBackground = true
)
@Composable
fun RecipeDetailsScreenPreview() {
    val recipe: RecipeUiModel = RecipesRepositoryStub.getRecipesByCategoryId(0)[0].toUiModel()
    RecipeDetailsScreen(recipe = recipe)
}
