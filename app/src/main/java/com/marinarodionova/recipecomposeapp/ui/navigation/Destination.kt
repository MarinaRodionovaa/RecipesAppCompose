package com.marinarodionova.recipecomposeapp.ui.navigation

sealed class Destination(val route: String) {
    data object Categories : Destination(ROUTE_CATEGORIES)
    data object Favorites : Destination(ROUTE_FAVORITES)
    object Recipes : Destination("$ROUTE_RECIPES/{categoryId}") {
        fun createRoute(categoryId: Int) = "$ROUTE_RECIPES/$categoryId"
    }

    companion object {
        private const val ROUTE_CATEGORIES = "categories"
        private const val ROUTE_RECIPES = "recipes"
        private const val ROUTE_FAVORITES = "favorites"
    }
}