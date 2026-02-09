package com.marinarodionova.recipecomposeapp.ui.navigation

import androidx.compose.runtime.Composable

import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.marinarodionova.recipecomposeapp.ui.categories.CategoriesScreen
import com.marinarodionova.recipecomposeapp.ui.details.RecipeDetailsScreen
import com.marinarodionova.recipecomposeapp.ui.favorites.FavoritesScreen
import com.marinarodionova.recipecomposeapp.ui.recipes.RecipesScreen
import com.marinarodionova.recipecomposeapp.ui.recipes.model.RecipeUiModel

@Composable
fun AppNavHost(
    navHostController: NavHostController
) {
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
            FavoritesScreen()
        }

        composable(
            route = Destination.Recipes.route,
            arguments = listOf(navArgument("categoryId") { type = NavType.IntType })
        ) { backStackEntry ->

            val categoryId = backStackEntry.arguments?.getInt("categoryId") ?: 0

            RecipesScreen(
                categoryId = categoryId,
                onRecipeClick = { recipeId, recipe ->
                    navHostController.currentBackStackEntry
                        ?.savedStateHandle
                        ?.set(KEY_RECIPE_OBJECT, recipe)

                    navHostController.navigate(
                        Destination.RecipeDetails.createRoute(recipeId)
                    )
                }
            )
        }

        composable(Destination.RecipeDetails.route) {
            val recipe = navHostController.previousBackStackEntry
                ?.savedStateHandle
                ?.get<RecipeUiModel>(KEY_RECIPE_OBJECT)
            if (recipe != null) {
                RecipeDetailsScreen(recipe = recipe)
            }
        }
    }
}
