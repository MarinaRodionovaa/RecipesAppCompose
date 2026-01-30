package com.marinarodionova.recipecomposeapp.ui.core

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.marinarodionova.recipecomposeapp.R
import com.marinarodionova.recipecomposeapp.ui.theme.Dimens
import com.marinarodionova.recipecomposeapp.ui.theme.RecipeComposeAppTheme

@Composable
fun ScreenHeader(
    title: String? = null,
    @StringRes titleResId: Int? = null,
    imageUrl: String? = null,
    @DrawableRes imageResId: Int? = null,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(Dimens.headerHeight),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl ?: imageResId)
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        )
        Surface(
            color = MaterialTheme.colorScheme.background,
            shape = RoundedCornerShape(Dimens.radiusSmall),
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = Dimens.standardPadding, bottom = Dimens.standardPadding)
        ) {
            Text(
                text = (title ?: (titleResId?.let { stringResource(id = it) } ?: "")).uppercase(),
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(Dimens.ttlPadding)
            )
        }
    }
}

@Preview(name = "LightTheme")
@Composable
fun ScreenHeaderLightPreview() {
    RecipeComposeAppTheme {
        ScreenHeader(
            imageResId = R.drawable.bcg_categories,
            titleResId = R.string.title_category
        )
    }
}
