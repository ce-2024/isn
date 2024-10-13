package com.instantsystem.android.feature.news.ui

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.instantsystem.android.core.designsystem.theme.InstantSystemNewsTheme
import com.instantsystem.android.feature.news.ui.tag.NewsHomeScreenTestTags


@Composable
fun ShimmerNewsArticleItem() {
    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.2f),
        Color.LightGray,
        Color.LightGray.copy(alpha = 0.1f),
    )

    val transition = rememberInfiniteTransition(label = "")
    val translateAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset.Zero,
        end = Offset(x = translateAnim.value, y = translateAnim.value)
    )

    ShimmerNewsArticleItemContent(brush = brush)
}

@Composable
private fun ShimmerNewsArticleItemContent(brush: Brush) {
    val defaultPadding = 10.dp
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag(NewsHomeScreenTestTags.NEWS_ARTICLE_ITEM_SCREEN)
            .padding(10.dp)

    ) {
        // Date placeholder
        Text(
            text = "",
            modifier = Modifier
                .fillMaxWidth()
                .padding(defaultPadding),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.labelSmall,
        )
        // Title placeholder
        Text(
            text = "",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = defaultPadding)
                .background(brush),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.titleLarge,
        )
        // Article image placeholder
        AsyncImage(
            modifier = Modifier
                .requiredHeight(256.dp)
                .fillMaxWidth()
                .padding(defaultPadding)
                .clip(RoundedCornerShape(8.dp))
                .background(brush),
            model = null,
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )
        // Description placeholder
        Text(
            text = "",
            modifier = Modifier
                .fillMaxWidth()
                .padding(defaultPadding)
                .background(brush),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Preview
@Composable
private fun ShimmerNewsArticleItemPreview() {
    InstantSystemNewsTheme {
        Surface {
            ShimmerNewsArticleItem()
        }
    }
}