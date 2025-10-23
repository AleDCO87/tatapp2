package com.example.tatapp.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Badge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tatapp.R

data class BottomItem(
    val id: String,
    @DrawableRes val iconRes: Int,
    val contentDescription: String,
    val label: String,
    val badgeCount: Int = 0,
    val tintIcon: Boolean = false
)

@Composable
fun BottomHomeBar(
    carritoCount: Int = 0,
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
    onItemSelected: (String) -> Unit,

) {
    val items = listOf(
        BottomItem(
            id = "home",
            iconRes = R.drawable.ico_home,
            contentDescription = "Inicio",
            label = "Home",
            tintIcon = true
        ),
        BottomItem(
            id = "detalle",
            iconRes = R.drawable.ico_detalle,
            contentDescription = "Detalle",
            label = "Detalle",
            tintIcon = true
        ),
        BottomItem(
            id = "carrito",
            iconRes = R.drawable.ico_carrito,
            contentDescription = "Carrito",
            label = "Carrito",
            tintIcon = true,
            badgeCount = carritoCount
        ),
        BottomItem(
            id = "perfil",
            iconRes = R.drawable.ico_perfil,
            contentDescription = "Perfil",
            label = "Perfil",
            tintIcon = true
        ),
        BottomItem(
            id = "logo",
            iconRes = R.drawable.ico_loro,
            contentDescription = "Loro",
            label = "Loro",
            tintIcon = false
        )
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .height(80.dp)
            .navigationBarsPadding()
            .padding(horizontal = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceAround,
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
                    onClick = { onItemSelected(item.id) }
                )
            }
        }
    }
}

@Composable
fun BottomBarItem(
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
    iconSize: Dp = 38.dp,
    labelFontSize: TextUnit = 13.sp
) {
    val scale by animateFloatAsState(
        targetValue = if (selected) 1.25f else 1.0f,
        animationSpec = spring(
            dampingRatio = 0.6f, // menos rebote
            stiffness = 300f      // más suave
        )
    )

    val tint = if (item.tintIcon) {
        if (selected) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurface
    } else {
        Color.Unspecified
    }

    Column(
        modifier = Modifier
            .width(65.dp)
            .clickable { onClick() },
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
        Box(
            modifier = Modifier
                .size(36.dp)
                .scale(scale),

            contentAlignment = Alignment.TopEnd
        ) {
            Image(
                painter = painterResource(id = item.iconRes),
                contentDescription = item.contentDescription,
                modifier = Modifier.fillMaxSize(),
                colorFilter = if (tint != Color.Unspecified)
                    androidx.compose.ui.graphics.ColorFilter.tint(tint)
                else null
            )

            if (item.badgeCount > 0) {
                Badge(
                    modifier = Modifier
                        .offset(x = 8.dp, y = (-4).dp)
                        .size(18.dp),
                    containerColor = Color.Red
                ) {
                    Text(
                        text = item.badgeCount.toString(),
                        color = Color.White,
                        fontSize = 10.sp
                    )
                }
            }
        }

        if (selected) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = item.label,
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = labelFontSize,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }


}