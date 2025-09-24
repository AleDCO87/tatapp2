package com.example.tatapp.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Badge
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class BottomItem(
    val id: String,
    @DrawableRes val iconRes: Int,
    val contentDescription: String,
    val label: String? = null,
    val showLabelAlways: Boolean = false,
    val badgeCount: Int = 0,
    val iconSize: Dp? = null,
    val labelFontSize: TextUnit? = null,
    val itemWidth: Dp? = null,
    val tintIcon: Boolean = true
)

@Composable
fun BottomHomeBar(
    items: List<BottomItem>,
    selectedId: String,
    onItemSelected: (BottomItem) -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    badgeColor: Color = MaterialTheme.colorScheme.error,
    barHeight: Dp = 137.dp,
    itemWidth: Dp = 68.dp,
    iconSize: Dp = 60.dp,
    labelFontSize: TextUnit = 16.sp
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .height(barHeight)
            .windowInsetsPadding(WindowInsets.navigationBars.only(WindowInsetsSides.Bottom))
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEach { item ->
                BottomBarItem(
                    item = item,
                    selected = item.id == selectedId,
                    onClick = { onItemSelected(item) },
                    iconTint = contentColor,
                    labelColor = contentColor,
                    itemWidth = item.itemWidth ?: itemWidth,
                    iconSize = iconSize,
                    labelFontSize = labelFontSize
                )
            }
        }
    }
}

@Composable
private fun BottomBarItem(
    item: BottomItem,
    selected: Boolean,
    onClick: () -> Unit,
    iconTint: Color,
    labelColor: Color,
    itemWidth: Dp = 60.dp,
    iconSize: Dp = 30.dp,
    labelFontSize: TextUnit = 16.sp,
    badgeColor: Color = MaterialTheme.colorScheme.error
) {
    val effectiveIconSize = item.iconSize ?: iconSize
    val effectiveLabelSize = item.labelFontSize ?: labelFontSize
    val effectiveTint = if (item.tintIcon) iconTint else Color.Unspecified

    Column(
        modifier = Modifier
            .width(itemWidth)
            .heightIn(min = 56.dp)
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Box(
            modifier = Modifier.requiredSize(effectiveIconSize), // <- corregido
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = item.iconRes),
                contentDescription = item.contentDescription,
                modifier = Modifier.fillMaxSize(),
                tint = effectiveTint
            )
            if (item.badgeCount > 0) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .offset(x = 8.dp, y = (-8).dp)
                ) {
                    Badge(
                        modifier = Modifier.size(25.dp),
                        containerColor = badgeColor
                    ) {
                        Text(
                            text = item.badgeCount.toString(),
                            color = Color.White,
                            fontSize = 20.sp
                        )
                    }
                }
            }
        }

        if (item.label != null && (item.showLabelAlways || selected)) {
            Spacer(Modifier.height(6.dp))
            Text(
                text = item.label,
                color = labelColor,
                fontSize = effectiveLabelSize,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                maxLines = 1,
                softWrap = false,
                modifier = Modifier.width(itemWidth)
            )
        }
    }
}
