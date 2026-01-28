package com.marinarodionova.recipecomposeapp.ui.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.marinarodionova.recipecomposeapp.R
import com.marinarodionova.recipecomposeapp.ui.theme.Dimens
import com.marinarodionova.recipecomposeapp.ui.theme.Dimens.radiusSmall
import com.marinarodionova.recipecomposeapp.ui.theme.Dimens.standardPadding
import com.marinarodionova.recipecomposeapp.ui.theme.Dimens.ttlPadding
import com.marinarodionova.recipecomposeapp.ui.theme.RecipeComposeAppTheme

@Composable
fun BottomNavigation(
    onRecipesClick: () -> Unit,
    onCategoriesClick: () -> Unit,
    onFavoriteClick: () -> Unit
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = standardPadding)
        ) {
            Button(
                onClick = onRecipesClick,
                shape = RoundedCornerShape(radiusSmall),
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiary),
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    text = stringResource(R.string.title_recipe).uppercase(),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.surface
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = standardPadding)
                .padding(
                    top = Dimens.halfMargin,
                    bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
                )
        ) {
            Button(
                onClick = onCategoriesClick,
                shape = RoundedCornerShape(radiusSmall),
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiary),
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    text = stringResource(R.string.title_category).uppercase(),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.surface
                )
            }
            Spacer(modifier = Modifier.size(Dimens.smallMargin))
            Button(
                onClick = onFavoriteClick,
                shape = RoundedCornerShape(radiusSmall),
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.error),
            ) {
                Text(
                    text = stringResource(R.string.title_favorite).uppercase(),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSecondary,
                    modifier = Modifier.padding(end = ttlPadding)
                )
                Icon(
                    painter = painterResource(R.drawable.ic_heart_empty),
                    contentDescription = "Добавить в избранное",
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colorScheme.surface
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
fun BottomNavigationLightPreview() {
    RecipeComposeAppTheme {
        BottomNavigation(
            onCategoriesClick = {},
            onFavoriteClick = {},
            onRecipesClick = {}
        )
    }
}

@Preview(
    name = "DarkTheme",
    showBackground = true,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun BottomNavigationDarkPreview() {
    RecipeComposeAppTheme {
        BottomNavigation(
            onCategoriesClick = {},
            onFavoriteClick = {},
            onRecipesClick = {}
        )
    }
}
