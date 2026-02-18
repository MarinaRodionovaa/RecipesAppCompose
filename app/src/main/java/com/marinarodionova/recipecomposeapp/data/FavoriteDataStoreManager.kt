package com.marinarodionova.recipecomposeapp.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.distinctUntilChanged

class FavoriteDataStoreManager(context: Context) {
    private val preferences = context.dataStore

    fun getFavoriteIdsFlow(): Flow<Set<String>> {
        return preferences.data
            .map { prefs -> prefs[PreferencesKeys.FAVORITE_RECIPE_IDS] ?: emptySet() }
            .distinctUntilChanged()
    }

    fun isFavoriteFlow(recipeId: Int): Flow<Boolean> {
        return getFavoriteIdsFlow().map { favoriteIds ->
            favoriteIds.contains(recipeId.toString())
        }
    }

    fun getFavoriteCountFlow(): Flow<Int> {
        return getFavoriteIdsFlow().map { it.size }
    }

    suspend fun addFavorite(recipeId: Int) {
        preferences.edit { preferences ->
            val currentFavorites = preferences[PreferencesKeys.FAVORITE_RECIPE_IDS] ?: emptySet()
            val updatedFavorites = currentFavorites + recipeId.toString()
            preferences[PreferencesKeys.FAVORITE_RECIPE_IDS] = updatedFavorites
        }
    }

    suspend fun removeFavorite(recipeId: Int) {
        preferences.edit { preferences ->
            val currentFavorites = preferences[PreferencesKeys.FAVORITE_RECIPE_IDS] ?: emptySet()
            val updatedFavorites = currentFavorites - recipeId.toString()
            preferences[PreferencesKeys.FAVORITE_RECIPE_IDS] = updatedFavorites
        }
    }
}
