package com.marinarodionova.recipecomposeapp.core.utils

object Constants {
    const val ASSETS_URI_PREFIX = "file:///android_asset/"
    const val PARAM_RECIPE_ID = "recipeId"
    const val DEEP_LINK_SCHEME = "recipeapp"
    private const val DEEP_LINK_BASE_URL = "https://recipes.androidsprint.ru"

    fun createRecipeDeepLink(recipeId: Int): String {
        return "$DEEP_LINK_BASE_URL/recipe/$recipeId"
    }
}
