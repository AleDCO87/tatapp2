package com.example.tatapp.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun Home( navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Bienvenido a Tatapp", style = MaterialTheme.typography.displayMedium)
        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = { navController.navigate("homeProductosScreen") }) {
            Text(text = "Ya tengo cuenta")
        }
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = { navController.navigate("registro") }) {
            Text(text = "Crear cuenta")
        }
    }
}