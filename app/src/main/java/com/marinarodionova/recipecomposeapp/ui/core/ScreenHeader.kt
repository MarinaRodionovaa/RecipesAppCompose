package com.marinarodionova.recipecomposeapp.ui.core

import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.marinarodionova.recipecomposeapp.R
import com.marinarodionova.recipecomposeapp.ui.theme.Dimens
import com.marinarodionova.recipecomposeapp.ui.theme.RecipeComposeAppTheme

@Composable
fun ScreenHeader(
    title: String,
    imagePainter: Painter
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(Dimens.headerHeight),
        contentAlignment = Alignment.Center
    ) {
        Image(
            imagePainter,
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
                text = title.uppercase(),
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
            imagePainter = painterResource(R.drawable.bcg_categories),
            title = stringResource(R.string.title_category)
        )
    }
}
