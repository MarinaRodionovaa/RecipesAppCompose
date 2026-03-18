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
import kotlinx.serialization.json.Json
import java.net.HttpURLConnection
import java.net.URL
import kotlin.io.bufferedReader

class MainActivity : ComponentActivity() {
    private var deepLinkIntent by mutableStateOf<Intent?>(null)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent?.data?.let { _ ->
            deepLinkIntent = intent
        }
        enableEdgeToEdge()
        setContent {
            RecipesApp(deepLinkIntent = deepLinkIntent)
        }

        Log.i("!!!!", "Метод onCreate() выполняется на потоке: ${Thread.currentThread().getName()}")
        val thread = Thread {
            val url = URL("https://recipes.androidsprint.ru/api/category")
            val connection = url.openConnection() as HttpURLConnection
            connection.content
            val categories = connection.inputStream.bufferedReader().readText()
            connection.disconnect()
            Log.i("!!!!", "Body: $categories")
            Log.i("!!!!", "Выполняю запрос на потоке: ${Thread.currentThread().getName()}")
            val categoryList: List<CategoryDto> = Json.decodeFromString(categories)
            Log.i(
                "!!!!",
                "Кол-во категорий: ${categoryList.size},Название категорий: ${categoryList.map { it.title }} "
            )
        }
        thread.start()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        intent.data?.let { _ ->
            deepLinkIntent = intent
        }
        setIntent(intent)
    }
}
