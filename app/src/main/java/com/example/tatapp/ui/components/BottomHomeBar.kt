package com.example.tatapp.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Badge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
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