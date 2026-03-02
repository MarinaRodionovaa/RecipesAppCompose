package com.marinarodionova.recipecomposeapp.features.categories.ui

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
import com.marinarodionova.recipecomposeapp.R
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.marinarodionova.recipecomposeapp.core.ui.EmptyPlaceholder
import com.marinarodionova.recipecomposeapp.core.ui.ScreenHeader
import com.marinarodionova.recipecomposeapp.core.ui.theme.Dimens
import com.marinarodionova.recipecomposeapp.core.ui.theme.RecipeComposeAppTheme
import com.marinarodionova.recipecomposeapp.features.categories.presentation.CategoriesViewModel

@Composable
fun CategoriesScreen(
    modifier: Modifier = Modifier,
    onCategoryClick: (category: Int, title: String, imageUrl: String) -> Unit,
    viewModel: CategoriesViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Column(modifier = modifier) {
        ScreenHeader(
            stringResource(R.string.title_category),
            imageResId = R.drawable.bcg_categories
        )
        if (state.categoriesList.isEmpty()) {
            EmptyPlaceholder(text = stringResource(R.string.information_message_categories_error))
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(Dimens.standardMargin),
                verticalArrangement = Arrangement.spacedBy(Dimens.standardMargin),
                contentPadding = PaddingValues(Dimens.standardMargin),
            ) {
                items(state.categoriesList, key = { it.id }) { category ->
                    CategoryItem(
                        imageUrl = category.imageUrl,
                        title = category.title,
                        description = category.description,
                        onCLick = {
                            onCategoryClick(
                                category.id,
                                category.title,
                                category.imageUrl
                            )
                        }
                    )
                }
            }
        }
    }
}

@Preview(name = "LightTheme", showBackground = true)
@Composable
fun CategoriesScreenPreview() {
    RecipeComposeAppTheme {
        CategoriesScreen(
            onCategoryClick = { _, _, _ -> },
        )
    }
}
