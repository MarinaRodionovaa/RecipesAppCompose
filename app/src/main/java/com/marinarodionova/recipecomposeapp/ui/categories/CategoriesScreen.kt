package com.marinarodionova.recipecomposeapp.ui.categories

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.marinarodionova.recipecomposeapp.R
import androidx.compose.ui.tooling.preview.Preview
import com.marinarodionova.recipecomposeapp.data.repository.RecipesRepositoryStub
import com.marinarodionova.recipecomposeapp.ui.categories.components.CategoryItem
import com.marinarodionova.recipecomposeapp.ui.categories.model.toUiModel
import com.marinarodionova.recipecomposeapp.ui.core.ScreenHeader
import com.marinarodionova.recipecomposeapp.ui.theme.Dimens
import com.marinarodionova.recipecomposeapp.ui.theme.RecipeComposeAppTheme

@Composable
fun CategoriesScreen(
    modifier: Modifier = Modifier,
    onCategoryClick: (category: Int) -> Unit
) {
    val categories = RecipesRepositoryStub.getCategories()
    Column(modifier = modifier) {
        ScreenHeader(
            stringResource(R.string.title_category),
            imageResId = R.drawable.bcg_categories
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(Dimens.standardMargin),
            verticalArrangement = Arrangement.spacedBy(Dimens.standardMargin),
            contentPadding = PaddingValues(Dimens.standardMargin),
        ) {
            items(categories, key = { it.id }) { category ->
                val categoryUi = category.toUiModel()
                CategoryItem(
                    imageUrl = categoryUi.imageUrl,
                    title = categoryUi.title,
                    description = categoryUi.description,
                    onCLick = { onCategoryClick(categoryUi.id) }
                )
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
        CategoriesScreen(onCategoryClick = {})
    }
}
