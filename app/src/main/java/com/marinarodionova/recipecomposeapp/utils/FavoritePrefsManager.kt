package com.marinarodionova.recipecomposeapp.utils

import android.content.Context
import androidx.core.content.edit

class FavoritePrefsManager(context: Context) {
    val sharedPreferences = context.getSharedPreferences("recipe_app_prefs", Context.MODE_PRIVATE)

    fun isFavorite(recipeId: Int): Boolean {
        val favoriteRecipeIds = sharedPreferences
            .getStringSet("favorite_recipe_ids", emptySet())!!
        return recipeId.toString() in favoriteRecipeIds
    }

    fun addToFavorites(recipeId: Int) {
        val currentFavorites = sharedPreferences.getStringSet("favorite_recipe_ids", emptySet())
        val updatedFavorites = currentFavorites?.toMutableSet() ?: mutableSetOf()
        updatedFavorites.add(recipeId.toString())
        sharedPreferences.edit {
            putStringSet("favorite_recipe_ids", updatedFavorites)
        }
    }

    fun removeFromFavorites(recipeId: Int) {
        val currentFavorites = sharedPreferences.getStringSet("favorite_recipe_ids", emptySet())
        val updatedFavorites = currentFavorites?.toMutableSet() ?: mutableSetOf()
        updatedFavorites.remove(recipeId.toString())
        sharedPreferences.edit {
            putStringSet("favorite_recipe_ids", updatedFavorites)
        }
    }

    fun getAllFavorites(): Set<String> {
        return sharedPreferences.getStringSet("favorite_recipe_ids", emptySet())!!
    }
}
