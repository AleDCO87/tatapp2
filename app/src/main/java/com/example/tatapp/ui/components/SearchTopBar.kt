package com.example.tatapp.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.tatapp.R
import com.example.tatapp.ui.theme.bordeBarraBusqueda
import com.example.tatapp.ui.theme.colorBlanco
import com.example.tatapp.ui.theme.textoGris

@Composable
fun SearchTopBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    onVoiceClick: () -> Unit,
    isDark: Boolean,
    onToggleDark: () -> Unit,
    onOpenPerfil: () -> Unit,
    onOpenConfig: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding(),
        color = MaterialTheme.colorScheme.background,
        //tonalElevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            /*
            TopBarOverflowMenu(
                isDark = isDark,
                onToggleDark = onToggleDark,
                onOpenPerfil = onOpenPerfil,
                //onOpenConfig = onOpenConfig,
                trigger = {
                    Icon(
                        painter = painterResource(id = R.drawable.menu),
                        contentDescription = "Menu",
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.size(40.dp)
                    )
                }
            )

             */

            // Caja de búsqueda

            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(60.dp)
                    .border(
                        width = 3.dp, // Grosor del borde
                        color = bordeBarraBusqueda,
                        shape = RoundedCornerShape(30.dp)
                    )
            ) {OutlinedTextField(
                value = query,
                onValueChange = onQueryChange,
                singleLine = true,
                placeholder = { Text("Buscar en tatapp") },
                shape = RoundedCornerShape(30.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    //.weight(1f)
                    .height(60.dp),
                colors = TextFieldDefaults.colors(
                    focusedTextColor= textoGris,
                    unfocusedTextColor= textoGris,
                    focusedPlaceholderColor= textoGris,
                    unfocusedPlaceholderColor= textoGris,
                    focusedContainerColor = colorBlanco,
                    unfocusedContainerColor = colorBlanco,
                    disabledContainerColor = colorBlanco,
                    focusedIndicatorColor = bordeBarraBusqueda,
                    unfocusedIndicatorColor = bordeBarraBusqueda
                ),
                leadingIcon = {
                    IconButton(onClick = {  }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ico_colores),
                            contentDescription = "Buscar por voz",
                            modifier = Modifier.size(32.dp),
                            tint = Color.Unspecified
                        )
                    }
                },
                trailingIcon = {
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        IconButton(onClick = { onSearch(query) }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ico_lupa),
                                contentDescription = "Buscar",
                                tint = textoGris
                            )
                        }

                        IconButton(onClick = onVoiceClick) {
                            Icon(
                                painter = painterResource(id = R.drawable.ico_microfono),
                                contentDescription = "Buscar por voz",
                                tint = textoGris
                            )
                        }

                    }
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = { onSearch(query) })
            )}

        }
    }
}
