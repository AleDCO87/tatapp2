package com.example.tatapp.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Badge
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
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
    backgroundColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    contentColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    badgeColor: Color = MaterialTheme.colorScheme.error,
    barHeight: Dp = 135.dp,           // <- alto del BottomBar
    itemWidth: Dp = 68.dp,
    iconSize: Dp = 60.dp,
    labelFontSize: TextUnit = 16.sp,
    // Estilos del resaltado
    selectedCircleColor: Color = Color(0xFFF47606),
    selectedBorderColor: Color = Color.White,
    selectedLift: Dp = 58.dp,         // <- cuánto sube el ícono seleccionado
    selectedBorderWidth: Dp = 6.dp,
    selectedCircleShape: Shape = CircleShape,
    selectedBubbleSize: Dp? = null    // <- si null: usa (iconSize + 20.dp)
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
                    labelFontSize = labelFontSize,
                    badgeColor = badgeColor,
                    selectedCircleColor = selectedCircleColor,
                    selectedBorderColor = selectedBorderColor,
                    selectedLift = selectedLift,
                    selectedBorderWidth = selectedBorderWidth,
                    selectedCircleShape = selectedCircleShape,
                    selectedBubbleSize = selectedBubbleSize
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
    badgeColor: Color = Color.Red,
    // Estilos del resaltado
    selectedCircleColor: Color,
    selectedBorderColor: Color,
    selectedLift: Dp,
    selectedBorderWidth: Dp,
    selectedCircleShape: Shape,
    selectedBubbleSize: Dp?
) {
    val effectiveIconSize = item.iconSize ?: iconSize
    val effectiveLabelSize = item.labelFontSize ?: labelFontSize
    val effectiveTint = if (item.tintIcon) iconTint else Color.Unspecified
    val bubbleSize = selectedBubbleSize ?: (effectiveIconSize + 20.dp)

    Column(
        modifier = Modifier
            .width(itemWidth)
            .heightIn(min = 56.dp)
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (selected) {
            // Ícono dentro de burbuja naranja con borde blanco
            Box(
                modifier = Modifier
                    .size(bubbleSize)
                    .offset(y = -selectedLift)
                    .background(selectedCircleColor, selectedCircleShape)
                    .border(selectedBorderWidth, selectedBorderColor, selectedCircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = item.iconRes),
                    contentDescription = item.contentDescription,
                    modifier = Modifier.size(effectiveIconSize),
                    tint = effectiveTint
                )
                if (item.badgeCount > 0) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .offset(x = 6.dp, y = (-6).dp)
                    ) {
                        Badge(
                            modifier = Modifier.size(24.dp),
                            containerColor = badgeColor
                        ) {
                            Text(
                                text = item.badgeCount.toString(),
                                color = Color.White,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }
        } else {
            // Ícono normal (sin burbuja)
            Box(
                modifier = Modifier.requiredSize(effectiveIconSize),
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
