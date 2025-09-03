package com.example.tatapp.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun RequirementRow(
    text: String,
    satisfied: Boolean,
    neutral: Boolean,
){
    val success = Color(0xFF2E7D32)
    val error = MaterialTheme.colorScheme.error
    val onSurface = MaterialTheme.colorScheme.onSurface

    val color = when{
        neutral -> onSurface
        satisfied -> success
        else -> error
    }
    val icon = when{
        neutral -> null
        satisfied -> Icons.Default.CheckCircle
        else -> Icons.Default.Lock
    }

    Row{
        if (icon != null){
            Icon(icon, contentDescription = null, tint = color)
            Spacer (Modifier.width(8.dp))
        }
        Text(text = text, color = color, style = MaterialTheme.typography.labelSmall)
    }
}

@Composable
fun PasswordField(
    value: String,
    onValueChange: (String) -> Unit,
    errorText: String?,
    modifier: Modifier = Modifier,
    confirmValue: String? = null,
    minLength: Int = 6,
    label: String = "Contraseña"
) {
    var focused by remember { mutableStateOf(false) }
    var reveal by remember { mutableStateOf(false) }

    val showRules = focused || value.isNotEmpty()

    val neutral = value.isEmpty()
    val lenOk = value.length >= minLength
    val hasUpper = value.any { it.isUpperCase() }
    val hasDigit = value.any { it.isDigit() }
    val hasSpecial = value.any { !it.isLetterOrDigit() }
    val matchOk = confirmValue?.let{ it.isNotEmpty() && it == value } ?: false

    val hasError = errorText != null

    OutlinedTextField(
        value = value,
        onValueChange = {
            onValueChange(it)
        },
        label = { Text(label) },
        visualTransformation = if (reveal) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            TextButton(onClick = { reveal = !reveal }) {
                Text(if (reveal) "Ocultar" else "Mostrar")
            }
        },
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged { f -> focused = f.isFocused },
        singleLine = true,
        isError = hasError,
        supportingText = {
            when {
                hasError -> {
                    Text(
                        text = errorText ?: "",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
                showRules -> {
                    AnimatedVisibility(visible = showRules) {
                        Column{
                            Spacer(Modifier.height(6.dp))
                            RequirementRow(
                                text = "La contraseña debe tener más de 6 caracteres",
                                satisfied = lenOk,
                                neutral = neutral
                            )
                            RequirementRow(
                                text = "Debe contener al menos una mayúscula",
                                satisfied = hasUpper,
                                neutral = neutral
                            )
                            RequirementRow(
                                text = "Debe contener al menos un número",
                                satisfied = hasDigit,
                                neutral = neutral
                            )
                            RequirementRow(
                                text = "Debe contener al menos un símbolo (@, #, !, etc.)",
                                satisfied = hasSpecial,
                                neutral = neutral
                            )
                            if (confirmValue != null) {
                                RequirementRow(
                                    text = "Las contraseñas deben coincidir",
                                    satisfied = matchOk,
                                    neutral = confirmValue.isEmpty()
                                )
                            }
                        }
                    }
                }
            }
        }
    )
}
