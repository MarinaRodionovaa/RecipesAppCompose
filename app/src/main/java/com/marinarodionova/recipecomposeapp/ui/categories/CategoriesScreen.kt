package com.marinarodionova.recipecomposeapp.ui.categories

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.marinarodionova.recipecomposeapp.R
import androidx.compose.ui.tooling.preview.Preview
import com.marinarodionova.recipecomposeapp.ui.core.EmptyPlaceholder
import com.marinarodionova.recipecomposeapp.ui.core.ScreenHeader
import com.marinarodionova.recipecomposeapp.ui.theme.RecipeComposeAppTheme

@Composable
fun CategoriesScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        ScreenHeader(
            stringResource(R.string.title_category),
            painterResource(R.drawable.bcg_categories)
        )
        EmptyPlaceholder(text = stringResource(R.string.information_message_category_list))
    }
}

@Preview(
    name = "LightTheme",
    showBackground = true
)
@Composable
fun CategoriesScreenPreview() {
    RecipeComposeAppTheme {
        CategoriesScreen()
    }
}
