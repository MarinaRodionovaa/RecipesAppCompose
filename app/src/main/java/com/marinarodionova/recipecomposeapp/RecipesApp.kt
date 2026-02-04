package com.marinarodionova.recipecomposeapp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.marinarodionova.recipecomposeapp.ui.navigation.BottomNavigation
import com.marinarodionova.recipecomposeapp.ui.categories.CategoriesScreen
import com.marinarodionova.recipecomposeapp.ui.favorites.FavoritesScreen
import com.marinarodionova.recipecomposeapp.ui.recipes.RecipesScreen
import com.marinarodionova.recipecomposeapp.ui.theme.RecipeComposeAppTheme

@Composable
fun RecipesApp() {
    var currentScreen by remember { mutableStateOf(ScreenId.CATEGORIES) }
    var selectedCategoryId by remember { mutableStateOf<Int?>(null) }

    RecipeComposeAppTheme {
        Scaffold(
            containerColor = MaterialTheme.colorScheme.background,
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                BottomNavigation(
                    onCategoriesClick = { currentScreen = ScreenId.CATEGORIES },
                    onFavoriteClick = { currentScreen = ScreenId.FAVORITES },
                )
            }
        ) { paddingValues ->

            when (currentScreen) {
                ScreenId.CATEGORIES -> CategoriesScreen(
                    modifier = Modifier.padding(paddingValues),
                    onCategoryClick = { category ->
                        selectedCategoryId = category.id
                        currentScreen = ScreenId.RECIPES
                    }
                )

                ScreenId.FAVORITES -> FavoritesScreen(
                    modifier = Modifier.padding(paddingValues)
                )

                ScreenId.RECIPES -> {
                    val categoryId = selectedCategoryId

                    if (categoryId != null) {
                        RecipesScreen(
                            modifier = Modifier.padding(paddingValues),
                            categoryId = categoryId
                        )
                    } else {
                        currentScreen = ScreenId.CATEGORIES
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecipesAppPreview() {
    RecipesApp()
}
