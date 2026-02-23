package com.marinarodionova.recipecomposeapp.features.categories.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marinarodionova.recipecomposeapp.data.repository.RecipesRepositoryStub
import com.marinarodionova.recipecomposeapp.features.categories.presentation.model.CategoriesUiState
import com.marinarodionova.recipecomposeapp.features.categories.presentation.model.toUiModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CategoriesViewModel() : ViewModel() {
    private val _uiState = MutableStateFlow(CategoriesUiState())
    val uiState: StateFlow<CategoriesUiState> = _uiState.asStateFlow()

    init {
        Log.d("!!!!", "Инициализация CategoriesViewModel и обновление")
        loadCategories()
    }

    private fun loadCategories() {
        viewModelScope.launch {
            try {
                _uiState.update { currentState ->
                    currentState.copy(
                        categoriesList = RecipesRepositoryStub.getCategories()
                            .map { it.toUiModel() },
                    )
                }

            } catch (e: Exception) {
                Log.d("!!!!", "Ошибка загрузки данных! ${e.message}")
            }
        }
    }
}
