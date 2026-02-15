package com.marinarodionova.recipecomposeapp.ui.navigation

sealed class Destination(val route: String) {
    data object Categories : Destination(ROUTE_CATEGORIES)
    data object Favorites : Destination(ROUTE_FAVORITES)
    data object Recipes : Destination("$ROUTE_RECIPES/{categoryId}") {
        fun createRoute(categoryId: Int) = "$ROUTE_RECIPES/$categoryId"
    }

    data object RecipeDetails : Destination("$ROUTE_RECIPE_DETAILS/{recipeId}") {
        fun createRoute(recipeId: Int): String = "$ROUTE_RECIPE_DETAILS/$recipeId"
    }

    companion object {
        private const val ROUTE_CATEGORIES = "categories"
        private const val ROUTE_RECIPES = "recipes"
        private const val ROUTE_RECIPE_DETAILS = "recipe"
        private const val ROUTE_FAVORITES = "favorites"
    }
}

const val KEY_RECIPE_OBJECT = "KEY_RECIPE_OBJECT"