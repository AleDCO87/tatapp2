package com.example.tatapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp

@Composable
fun TopBarOverflowMenu(
    isDark: Boolean,
    onToggleDark: () -> Unit,
    onOpenPerfil: () -> Unit,
    //onOpenConfig: () -> Unit,
    closeOnToggle: Boolean = false,
    trigger: @Composable (() -> Unit)? = null
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .size(40.dp)
            .clickable { expanded = true },
        contentAlignment = Alignment.Center
    ) {
        if (trigger != null) {
            trigger()
        } else {
            Icon(Icons.Filled.AccountCircle, contentDescription = "Perfil")
        }
    }

    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }, containerColor = MaterialTheme.colorScheme.surface) {
        DropdownMenuItem(text = { Text("Perfil") }, onClick = { expanded = false; onOpenPerfil() })
        //DropdownMenuItem(text = { Text("Configuración") }, onClick = { expanded = false; onOpenConfig() })

        DropdownMenuItem(
            text = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Modo oscuro", modifier = Modifier.weight(1f))
                    Switch(
                        checked = isDark,
                        onCheckedChange = {
                            onToggleDark()
                            if (closeOnToggle) expanded = false
                        }
                    )
                }
            },
            onClick = { /* Switch maneja el click */ }
        )
    }
}