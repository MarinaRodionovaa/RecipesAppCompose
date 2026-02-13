package com.marinarodionova.recipecomposeapp.ui.details

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.marinarodionova.recipecomposeapp.ShareUtils
import com.marinarodionova.recipecomposeapp.data.repository.RecipesRepositoryStub
import com.marinarodionova.recipecomposeapp.ui.recipes.model.RecipeUiModel
import com.marinarodionova.recipecomposeapp.ui.recipes.model.toUiModel
import com.marinarodionova.recipecomposeapp.ui.theme.Dimens
import com.marinarodionova.recipecomposeapp.ui.theme.RecipeComposeAppTheme

@Composable
fun RecipeHeader(
    recipe: RecipeUiModel,
    context: Context,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(Dimens.headerHeight),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(recipe.imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        )

        ShareIconButton(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(Dimens.standardPadding),
            onClick = {
                ShareUtils.shareRecipe(
                    context = context,
                    recipeId = recipe.id.toString(),
                    recipeTitle = recipe.title,
                )
            }
        )

        Surface(
            color = MaterialTheme.colorScheme.background,
            shape = RoundedCornerShape(Dimens.radiusSmall),
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = Dimens.standardPadding, bottom = Dimens.standardPadding)
        ) {
            Text(
                text = recipe.title.uppercase(),
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(Dimens.ttlPadding)
            )
        }
    }
}
@Composable
fun ShareIconButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Default.Share,
            contentDescription = "",
            tint = MaterialTheme.colorScheme.outline
        )
    }
}

@Preview(name = "LightTheme")
@Composable
fun ScreenHeaderLightPreview() {
    val context = LocalContext.current
    RecipeComposeAppTheme {
        RecipeHeader(RecipesRepositoryStub.getRecipesByRecipeId(0).toUiModel(), context = context)
    }
}