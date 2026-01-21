package com.marinarodionova.recipecomposeapp.ui.recipes

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.marinarodionova.recipecomposeapp.R
import com.marinarodionova.recipecomposeapp.ui.core.EmptyPlaceholder
import com.marinarodionova.recipecomposeapp.ui.core.ScreenHeader

@Composable
fun RecipesScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        ScreenHeader(
            stringResource(R.string.title_recipe),
            painterResource(R.drawable.bcg_favorites)
        )
        EmptyPlaceholder(text = stringResource(R.string.information_message_recipe_list))
    }
}
