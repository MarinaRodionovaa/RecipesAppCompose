package com.marinarodionova.recipecomposeapp.ui.navigation

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.marinarodionova.recipecomposeapp.Constants
import com.marinarodionova.recipecomposeapp.data.FavoriteDataStoreManager
import com.marinarodionova.recipecomposeapp.ui.categories.CategoriesScreen
import com.marinarodionova.recipecomposeapp.ui.details.RecipeDetailsScreen
import com.marinarodionova.recipecomposeapp.ui.favorites.FavoritesScreen
import com.marinarodionova.recipecomposeapp.ui.recipes.RecipesScreen
import kotlinx.coroutines.delay

@Composable
fun AppNavHost(
    navHostController: NavHostController,
    deepLinkIntent: Intent?
) {
    val context = LocalContext.current
    val favoritePrefsManager = remember { FavoriteDataStoreManager(context) }
    LaunchedEffect(deepLinkIntent) {
        deepLinkIntent?.data?.let { uri ->
            val recipeId: Int? = when (uri.scheme) {
                Constants.DEEP_LINK_SCHEME ->
                    if (uri.host == "recipe") uri.pathSegments[0].toIntOrNull() else null

                "https", "http" ->
                    if (uri.pathSegments[0] == "recipe") uri.pathSegments[1].toIntOrNull() else null

                else -> null
            }

            if (recipeId != null) {
                delay(100)
                navHostController.navigate(Destination.RecipeDetails.createRoute(recipeId))
            }
        }
    }


    NavHost(
        navController = navHostController,
        startDestination = Destination.Categories.route
    ) {
        composable(route = Destination.Categories.route) {
            CategoriesScreen(
                onCategoryClick = { categoryId ->
                    navHostController.navigate(Destination.Recipes.createRoute(categoryId))
                }
            )
        }

        composable(route = Destination.Favorites.route) {
            FavoritesScreen(favoritePref = favoritePrefsManager, onRecipeClick = { recipeId ->
                navHostController.navigate(
                    Destination.RecipeDetails.createRoute(recipeId)
                )
            })
        }

        composable(
            route = Destination.Recipes.route,
            arguments = listOf(navArgument("categoryId") { type = NavType.IntType })
        ) { backStackEntry ->

            val categoryId = backStackEntry.arguments?.getInt("categoryId") ?: 0

            RecipesScreen(
                categoryId = categoryId,
                onRecipeClick = { recipeId ->
                    navHostController.navigate(
                        Destination.RecipeDetails.createRoute(recipeId)
                    )
                }
            )
        }

        composable(
            route = Destination.RecipeDetails.route,
            arguments = listOf(navArgument(Constants.PARAM_RECIPE_ID) { type = NavType.IntType })
        ) { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getInt(Constants.PARAM_RECIPE_ID) ?: 0
            RecipeDetailsScreen(
                recipeId = recipeId,
                favoritePrefsManager
            )
        }
    }
}
