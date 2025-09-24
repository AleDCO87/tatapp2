package com.example.tatapp.ui.screens.carrito

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.NumberFormat
import java.util.Locale

private fun formatCLP(v: Long): String =
    NumberFormat.getNumberInstance(Locale.forLanguageTag("es-CL")).format(v)

@Composable
fun ResumenPedidoCard(
    subtotal: Long,
    total: Long,
    onPagar: () -> Unit,
    onVaciar: () -> Unit,
    modifier: Modifier = Modifier
) {
    val descuento = (subtotal - total).coerceAtLeast(0)
    val pct = if (subtotal > 0) descuento.toDouble() / subtotal else 0.0

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(Modifier.padding(18.dp)) {
            Text(
                "Resumen de tu pedido",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(Modifier.height(12.dp))

            // filas de monto
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("SubTotal", color = MaterialTheme.colorScheme.onSurface)
                Text("$${formatCLP(subtotal)}", color = MaterialTheme.colorScheme.onSurface)
            }
            Spacer(Modifier.height(8.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Dctos %", color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text("$${formatCLP(descuento)}  (${(pct * 100).toInt()}%)",
                    color = MaterialTheme.colorScheme.onSurfaceVariant)
            }

            Spacer(Modifier.height(14.dp))

            // total destacado
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.surfaceVariant
            ) {
                Row(
                    Modifier.fillMaxWidth().padding(horizontal = 14.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Total", style = MaterialTheme.typography.titleMedium)
                    Text(
                        "$${formatCLP(total)}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(Modifier.height(14.dp))

            // botón pagar / continuar
            Button(
                onClick = onPagar,
                enabled = subtotal > 0,
                modifier = Modifier.fillMaxWidth().height(48.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Continuar", fontSize = 18.sp)
            }

            Spacer(Modifier.height(8.dp))

            // Vaciar carrito
            TextButton(
                onClick = onVaciar,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Vaciar Carrito")
            }
        }
    }
}
