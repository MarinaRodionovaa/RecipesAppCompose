package com.marinarodionova.recipecomposeapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.marinarodionova.recipecomposeapp.data.model.CategoryDto
import com.marinarodionova.recipecomposeapp.data.model.RecipeDto
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import java.net.URL
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainActivity : ComponentActivity() {
    private var deepLinkIntent by mutableStateOf<Intent?>(null)
    private val threadPool: ExecutorService = Executors.newFixedThreadPool(10)
    private val okHttpClient = OkHttpClient()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent?.data?.let { _ ->
            deepLinkIntent = intent
        }
        enableEdgeToEdge()
        setContent {
            RecipesApp(deepLinkIntent = deepLinkIntent)
        }

        Log.i("!!!!", "Метод onCreate() выполняется на потоке: ${Thread.currentThread().name}")
        threadPool.execute {
            try {
                val request =
                    Request.Builder().url("https://recipes.androidsprint.ru/api/category").build()
                val response = okHttpClient.newCall(request).execute()

                val categoriesJson = response.body?.string()

                Log.i("!POOL", "Запрос категорий: поток ${Thread.currentThread().name}")

                val categoryList: List<CategoryDto>? = categoriesJson?.let {
                    Json.decodeFromString(
                        it
                    )
                }

                Log.i(
                    "!!!!",
                    "Категорий: ${categoryList?.size}, ${categoryList?.map { it.title }}"
                )

                if (categoryList != null) {
                    for (category in categoryList) {
                        threadPool.execute {
                            try {
                                val urlRecipes = URL(
                                    "https://recipes.androidsprint.ru/api/category/${category.id}/recipes"
                                )
                                val requestRecipes = Request.Builder().url(urlRecipes).build()
                                val responseRecipes = okHttpClient.newCall(requestRecipes).execute()

                                val recipesJson = responseRecipes.body?.string()

                                Log.i(
                                    "!POOL",
                                    "Рецепты: поток ${Thread.currentThread().name}"
                                )

                                val recipeList: List<RecipeDto>? = recipesJson?.let {
                                    Json.decodeFromString(
                                        it
                                    )
                                }

                                Log.i(
                                    "!!!!",
                                    "Поток: ${Thread.currentThread().name}, категория: ${category.title}, рецептов: ${recipeList?.size}"
                                )

                            } catch (e: Exception) {
                                Log.e(
                                    "!ERROR",
                                    "Ошибка при загрузке рецептов для категории: ${category.title} (id=${category.id})",
                                    e
                                )
                            }
                        }
                    }
                }

            } catch (e: Exception) {
                Log.e(
                    "!ERROR",
                    "Ошибка при загрузке списка категорий",
                    e
                )
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        intent.data?.let { _ ->
            deepLinkIntent = intent
        }
        setIntent(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        threadPool.shutdown()
    }
}
