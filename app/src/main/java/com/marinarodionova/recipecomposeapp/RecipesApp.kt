package com.marinarodionova.recipecomposeapp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.marinarodionova.recipecomposeapp.ui.navigation.BottomNavigation
import com.marinarodionova.recipecomposeapp.ui.navigation.AppNavHost
import com.marinarodionova.recipecomposeapp.ui.navigation.Destination
import com.marinarodionova.recipecomposeapp.ui.theme.RecipeComposeAppTheme

@Composable
fun RecipesApp() {
    val navController = rememberNavController()

    RecipeComposeAppTheme {
        Scaffold(
            containerColor = MaterialTheme.colorScheme.background,
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                BottomNavigation(
                    onCategoriesClick = { navController.navigate(Destination.Categories.route) },
                    onFavoriteClick = { navController.navigate(Destination.Favorites.route) }
                )
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            )
            {
                AppNavHost(navController)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecipesAppPreview() {
    RecipesApp()
}
