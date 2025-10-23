package com.example.tatapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
            .statusBarsPadding()
            .padding(horizontal = 12.dp, vertical = 8.dp),
        color = MaterialTheme.colorScheme.surface
            .statusBarsPadding(),
        color = MaterialTheme.colorScheme.background,
        //tonalElevation = 2.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(
                    color = MaterialTheme.colorScheme.background,
                    shape = RoundedCornerShape(15.dp)
                )
                .border(
                    width = 1.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(15.dp)
                )
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { onSearch(query) },
                    modifier = Modifier.size(30.dp)
                ) {
            /*
            TopBarOverflowMenu(
                isDark = isDark,
                onToggleDark = onToggleDark,
                onOpenPerfil = onOpenPerfil,
                //onOpenConfig = onOpenConfig,
                trigger = {
                    Icon(
                        painter = painterResource(id = R.drawable.lupa),
                        contentDescription = "Buscar",
                        tint = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.size(25.dp)
                    )
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    if (query.isEmpty()) {
                        Text(
                            text = "Hola, busca aquí ...",
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                            fontSize = 16.sp
                        )
                    }
                    BasicTextField(
                        value = query,
                        onValueChange = onQueryChange,
                        singleLine = true,
                        textStyle = TextStyle(
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 16.sp
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                IconButton(
                    onClick = onVoiceClick,
                    modifier = Modifier.size(30.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.mic),
                        contentDescription = "Buscar por voz",
                        tint = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.size(25.dp)
                    )
                }
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
