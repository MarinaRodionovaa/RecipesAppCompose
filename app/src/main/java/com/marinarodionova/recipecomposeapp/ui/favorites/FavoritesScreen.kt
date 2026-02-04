package com.marinarodionova.recipecomposeapp.ui.favorites

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.marinarodionova.recipecomposeapp.R
import com.marinarodionova.recipecomposeapp.ui.core.EmptyPlaceholder
import com.marinarodionova.recipecomposeapp.ui.core.ScreenHeader
import com.marinarodionova.recipecomposeapp.ui.theme.RecipeComposeAppTheme

@Composable
fun FavoritesScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        ScreenHeader(
            stringResource(R.string.title_favorite),
            imageResId = R.drawable.bcg_favorites
        )
        EmptyPlaceholder(text = stringResource(R.string.information_message_favorite_list))
    }
}

@Preview()
@Composable
fun FavoritesScreenPreview() {
    RecipeComposeAppTheme {
        FavoritesScreen()
    }
}
