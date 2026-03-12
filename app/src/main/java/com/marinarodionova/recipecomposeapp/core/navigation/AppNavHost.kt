package com.marinarodionova.recipecomposeapp.core.navigation

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.marinarodionova.recipecomposeapp.core.utils.Constants
import com.marinarodionova.recipecomposeapp.features.categories.ui.CategoriesScreen
import com.marinarodionova.recipecomposeapp.features.details.presentation.RecipeDetailsViewModel
import com.marinarodionova.recipecomposeapp.features.details.ui.RecipeDetailsScreen
import com.marinarodionova.recipecomposeapp.features.favorites.ui.FavoritesScreen
import com.marinarodionova.recipecomposeapp.features.recipes.presentation.RecipesViewModel
import com.marinarodionova.recipecomposeapp.features.recipes.ui.RecipesScreen
import kotlinx.coroutines.delay

@Composable
fun AppNavHost(
    navHostController: NavHostController,
    deepLinkIntent: Intent?
) {
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
                onCategoryClick = { categoryId, title, imageUrl ->
                    navHostController.navigate(
                        Destination.Recipes.createRoute(
                            categoryId = categoryId,
                            title = title,
                            imageUrl = imageUrl
                        )
                    )
                }
            )
        }

        composable(route = Destination.Favorites.route) {
            FavoritesScreen(
                onRecipeClick = { recipeId ->
                    navHostController.navigate(
                        Destination.RecipeDetails.createRoute(recipeId)
                    )
                },
            )
        }

        composable(
            route = Destination.Recipes.route,
            arguments = listOf(
                navArgument("categoryId") { type = NavType.IntType },
                navArgument("categoryTitle") { type = NavType.StringType },
                navArgument("categoryImageUrl") { type = NavType.StringType })
        ) { backStackEntry ->
            val viewModel: RecipesViewModel = viewModel(backStackEntry)
            RecipesScreen(
                viewModel = viewModel,
                onRecipeClick = { recipeId ->
                    navHostController.navigate(
                        Destination.RecipeDetails.createRoute(recipeId)
                    )
                },
            )
        }

        composable(
            route = Destination.RecipeDetails.route,
            arguments = listOf(navArgument(Constants.PARAM_RECIPE_ID) { type = NavType.IntType })
        ) { backStackEntry ->
            val viewModel: RecipeDetailsViewModel = viewModel(backStackEntry)
            RecipeDetailsScreen(viewModel = viewModel)
        }
    }
}
