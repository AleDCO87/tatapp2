package com.example.tatapp.ui.screens.categorias

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.tatapp.R
import com.example.tatapp.ui.screens.productos.ProductosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriasScreen(navController: NavHostController) {

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Categorías", fontSize = 30.sp) },
                /*
                navigationIcon = {
                    IconButton(onClick = { /* Abrir menú */ }) {
                        Icon(Icons.Rounded.Menu, contentDescription = "Menú")
                    }
                },*/
                /*
                actions = {
                    IconButton(onClick = { /* Ir al perfil */ }) {
                        Icon(Icons.Outlined.AccountCircle, contentDescription = "Perfil")
                    }
                }
                */    //para despues
            )
        },

    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)
            .padding(8.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Spacer (modifier = Modifier.height(10.dp))

            Text (text="¿Qué necesitas hoy?",
                fontSize = 25.sp)

            Spacer (modifier = Modifier.height(10.dp))

            Row (horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
                ){

                OutlinedButton(
                    onClick = { navController.navigate("productos") },
                    shape = RoundedCornerShape(16.dp), // bordes redondeados
                    border = BorderStroke(2.dp, Color.Gray), // grosor y color del borde
                    modifier = Modifier
                        .padding(5.dp)
                        .weight(1f)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Spacer(modifier = Modifier.height(5.dp))
                        Image(
                            painter = painterResource(id = R.drawable.ej_alim),
                            contentDescription = "Imagen del botón",
                            modifier = Modifier.fillMaxWidth(),
                            contentScale = ContentScale.FillWidth
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Text("Alimentación",
                            fontSize = 17.sp)
                    }
                }

                OutlinedButton(
                    onClick = { navController.navigate("productos") },
                    shape = RoundedCornerShape(16.dp), // bordes redondeados
                    border = BorderStroke(2.dp, Color.Gray), // grosor y color del borde
                    modifier = Modifier
                        .padding(5.dp)
                        .weight(1f)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Spacer(modifier = Modifier.height(5.dp))
                        Image(
                            painter = painterResource(id = R.drawable.ej_alim),
                            contentDescription = "Imagen del botón",
                            modifier = Modifier.fillMaxWidth(),
                            contentScale = ContentScale.FillWidth
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Text("Salud",
                            fontSize = 17.sp)
                    }
                }




            }
            Row (horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ){

                OutlinedButton(
                    onClick = { navController.navigate("productos") },
                    shape = RoundedCornerShape(16.dp), // bordes redondeados
                    border = BorderStroke(2.dp, Color.Gray), // grosor y color del borde
                    modifier = Modifier
                        .padding(5.dp)
                        .weight(1f)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Spacer(modifier = Modifier.height(5.dp))
                        Image(
                            painter = painterResource(id = R.drawable.ej_alim),
                            contentDescription = "Imagen del botón",
                            modifier = Modifier.fillMaxWidth(),
                            contentScale = ContentScale.FillWidth
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Text("Hogar",
                            fontSize = 17.sp)
                    }
                }

                OutlinedButton(
                    onClick = { navController.navigate("productos") },
                    shape = RoundedCornerShape(16.dp), // bordes redondeados
                    border = BorderStroke(2.dp, Color.Gray), // grosor y color del borde
                    modifier = Modifier
                        .padding(5.dp)
                        .weight(1f)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Spacer(modifier = Modifier.height(5.dp))
                        Image(
                            painter = painterResource(id = R.drawable.ej_alim),
                            contentDescription = "Imagen del botón",
                            modifier = Modifier.fillMaxWidth(),
                            contentScale = ContentScale.FillWidth
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Text("Vestuario",
                            fontSize = 17.sp)
                    }
                }




            }

            Row (horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ){

                OutlinedButton(
                    onClick = { navController.navigate("productos") },
                    shape = RoundedCornerShape(16.dp), // bordes redondeados
                    border = BorderStroke(2.dp, Color.Gray), // grosor y color del borde
                    modifier = Modifier
                        .padding(5.dp)
                        .weight(1f)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Spacer(modifier = Modifier.height(5.dp))
                        Image(
                            painter = painterResource(id = R.drawable.ej_alim),
                            contentDescription = "Imagen del botón",
                            modifier = Modifier.fillMaxWidth(),
                            contentScale = ContentScale.FillWidth
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Text("Cuidados",
                            fontSize = 17.sp)
                    }
                }

                OutlinedButton(
                    onClick = { navController.navigate("productos") },
                    shape = RoundedCornerShape(16.dp), // bordes redondeados
                    border = BorderStroke(2.dp, Color.Gray), // grosor y color del borde
                    modifier = Modifier
                        .padding(5.dp)
                        .weight(1f)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Spacer(modifier = Modifier.height(5.dp))
                        Image(
                            painter = painterResource(id = R.drawable.ej_alim),
                            contentDescription = "Imagen del botón",
                            modifier = Modifier.fillMaxWidth(),
                            contentScale = ContentScale.FillWidth
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Text("Experiencias",
                            fontSize = 17.sp)
                    }
                }
            }
        }
    }
}