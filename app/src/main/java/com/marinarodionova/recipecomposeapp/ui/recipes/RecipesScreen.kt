package com.marinarodionova.recipecomposeapp.ui.recipes

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.marinarodionova.recipecomposeapp.R
import com.marinarodionova.recipecomposeapp.ui.EmptyPlaceholder
import com.marinarodionova.recipecomposeapp.ui.ScreenHeader

@Composable
fun RecipesScreen(modifier: Modifier = Modifier) {
    ScreenHeader(R.string.title_recipe, R.drawable.bcg_favorites)
    EmptyPlaceholder(text = stringResource(R.string.information_message_recipe_list))
}
