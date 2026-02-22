package com.marinarodionova.recipecomposeapp.features.details.ui

import android.content.Context
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.marinarodionova.recipecomposeapp.R
import com.marinarodionova.recipecomposeapp.core.utils.ShareUtils
import com.marinarodionova.recipecomposeapp.data.repository.RecipesRepositoryStub
import com.marinarodionova.recipecomposeapp.features.recipes.presentation.model.RecipeUiModel
import com.marinarodionova.recipecomposeapp.core.ui.theme.Dimens
import com.marinarodionova.recipecomposeapp.core.ui.theme.RecipeComposeAppTheme
import com.marinarodionova.recipecomposeapp.features.recipes.presentation.model.toUiModel

@Composable
fun RecipeHeader(
    recipe: RecipeUiModel,
    context: Context,
    modifier: Modifier = Modifier,
    isFavorite: Boolean,
    onFavoriteToggle: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(Dimens.headerHeight),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = remember(recipe.imageUrl) {
                ImageRequest.Builder(context)
                    .data(recipe.imageUrl)
                    .crossfade(true)
                    .build()
            },
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        )

        Crossfade(
            targetState = isFavorite,
            animationSpec = tween(durationMillis = 300),
            label = "favorite_animation",
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = Dimens.iconMarginHorizontal, end = Dimens.iconMarginVertical)
        ) { isCurrentlyFavorite ->
            val heartIcon = rememberVectorPainter(
                image = ImageVector.vectorResource(
                    id = if (isCurrentlyFavorite) R.drawable.ic_heart else R.drawable.ic_heart_empty
                )
            )

            Icon(
                painter = heartIcon,
                contentDescription = "Favorite",
                tint = Color.Unspecified,
                modifier = Modifier
                    .size(Dimens.iconHeartSize)
                    .clickable {
                        onFavoriteToggle()
                    },
            )
        }

        ShareIconButton(
            modifier = Modifier
                .align(Alignment.TopStart)
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
        RecipeHeader(
            RecipesRepositoryStub.getRecipesByRecipeId(0).toUiModel(),
            context = context,
            isFavorite = false,
            onFavoriteToggle = {})
    }
}