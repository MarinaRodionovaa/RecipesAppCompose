package com.marinarodionova.recipecomposeapp.ui.favorites

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.marinarodionova.recipecomposeapp.R
import com.marinarodionova.recipecomposeapp.ui.EmptyPlaceholder
import com.marinarodionova.recipecomposeapp.ui.ScreenHeader
import com.marinarodionova.recipecomposeapp.ui.theme.RecipeComposeAppTheme

@Composable
fun FavoritesScreen(modifier: Modifier = Modifier) {
    ScreenHeader(R.string.title_favorite, R.drawable.bcg_favorites)
    EmptyPlaceholder(text = stringResource(R.string.information_message_favorite_list))
}

@Preview()
@Composable
fun FavoritesScreenPreview() {
    RecipeComposeAppTheme {
        FavoritesScreen()
    }
}
