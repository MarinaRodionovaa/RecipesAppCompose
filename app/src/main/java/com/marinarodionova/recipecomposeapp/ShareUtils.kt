package com.marinarodionova.recipecomposeapp

import android.content.Context
import android.content.Intent

object ShareUtils {
    fun shareRecipe(context: Context, recipeId: String, recipeTitle: String) {
        val shareLink = Constants.createRecipeDeepLink(recipeId.toInt())
        val shareText = "Попробуй этот рецепт: $recipeTitle\n$shareLink"

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, shareText)
        }

        context.startActivity(Intent.createChooser(intent, "Поделиться рецептом"))
    }
}