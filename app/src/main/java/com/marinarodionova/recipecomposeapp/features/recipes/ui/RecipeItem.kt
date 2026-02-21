package com.marinarodionova.recipecomposeapp.features.recipes.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.marinarodionova.recipecomposeapp.R
import com.marinarodionova.recipecomposeapp.core.ui.theme.Dimens
import com.marinarodionova.recipecomposeapp.core.ui.theme.RecipeComposeAppTheme

@Composable
fun RecipeItem(
    modifier: Modifier = Modifier,
    imageUrl: String,
    title: String,
    onCLick: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(Dimens.cardRecipeHeight)
            .shadow(
                elevation = Dimens.cardElevation,
                shape = RoundedCornerShape(Dimens.radiusSmall)
            )
            .clip(RoundedCornerShape(Dimens.radiusSmall))
            .clickable(onClick = onCLick),
        shape = RoundedCornerShape(Dimens.radiusSmall)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                placeholder = painterResource(R.drawable.img_placeholder),
                error = painterResource(R.drawable.img_error),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimens.cardImageRecipeHeight),
                contentScale = ContentScale.Crop
            )

            Text(
                text = title.uppercase(),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(Dimens.halfMargin)
            )
        }
    }
}

@Preview(
    name = "LightTheme",
    showBackground = true
)
@Composable
fun CategoryItemPreview() {
    RecipeComposeAppTheme {
        RecipeItem(
            imageUrl = "",
            title = "Чизбургер",
            onCLick = {}
        )
    }
}
