package com.example.tatapp.ui.screens.productos

import com.example.tatapp.R

val productosBase = listOf(
    // ALIMENTOS
    ClaseProductos(
        nombre = "Manzanas",
        precio = 2000,
        imagenRes = R.drawable.ej_alim,
        descripcion = "Manzanas frescas de temporada",
        categoria = CategoriaProducto.ALIMENTOS,
        subcategoria = "Frutas y Verduras",
        evaluacion = 4.5f
    ),
    ClaseProductos(
        nombre = "Palta",
        precio = 1500,
        imagenRes = R.drawable.ej_alim,
        descripcion = "Paltas maduras y cremosas",
        categoria = CategoriaProducto.ALIMENTOS,
        subcategoria = "Frutas y Verduras",
        evaluacion = 4.2f
    ),
    ClaseProductos(
        nombre = "Nueces",
        precio = 2000,
        imagenRes = R.drawable.ej_alim,
        descripcion = "Nueces frescas y saludables",
        categoria = CategoriaProducto.ALIMENTOS,
        subcategoria = "Frutas y Verduras",
        evaluacion = 4.7f
    ),
    ClaseProductos(
        nombre = "Garbanzos",
        precio = 1800,
        imagenRes = R.drawable.ej_alim,
        descripcion = "Garbanzos secos para cocinar",
        categoria = CategoriaProducto.ALIMENTOS,
        subcategoria = "Cereales",
        evaluacion = 4.1f
    ),
    ClaseProductos(
        nombre = "Avena",
        precio = 2500,
        imagenRes = R.drawable.ej_alim,
        descripcion = "Avena integral para desayunos",
        categoria = CategoriaProducto.ALIMENTOS,
        subcategoria = "Cereales",
        evaluacion = 4.3f
    ),
    ClaseProductos(
        nombre = "Lentejas",
        precio = 2000,
        imagenRes = R.drawable.ej_alim,
        descripcion = "Lentejas nutritivas y versátiles",
        categoria = CategoriaProducto.ALIMENTOS,
        subcategoria = "Cereales",
        evaluacion = 4.0f
    ),
    ClaseProductos(
        nombre = "Huevos",
        precio = 2500,
        imagenRes = R.drawable.ej_alim,
        descripcion = "Huevos frescos de granja",
        categoria = CategoriaProducto.ALIMENTOS,
        subcategoria = "Lácteos",
        evaluacion = 4.6f
    ),
    ClaseProductos(
        nombre = "Leche",
        precio = 1800,
        imagenRes = R.drawable.ej_alim,
        descripcion = "Leche fresca pasteurizada",
        categoria = CategoriaProducto.ALIMENTOS,
        subcategoria = "Lácteos",
        evaluacion = 4.4f
    ),
    ClaseProductos(
        nombre = "Yogur griego",
        precio = 2000,
        imagenRes = R.drawable.ej_alim,
        descripcion = "Yogur griego natural",
        categoria = CategoriaProducto.ALIMENTOS,
        subcategoria = "Lácteos",
        evaluacion = 4.8f
    ),
    ClaseProductos(
        nombre = "Pescado graso",
        precio = 3200,
        imagenRes = R.drawable.ej_alim,
        descripcion = "Pescado rico en Omega 3",
        categoria = CategoriaProducto.ALIMENTOS,
        subcategoria = "Carnes",
        evaluacion = 4.9f
    ),
    ClaseProductos(
        nombre = "Pollo",
        precio = 2800,
        imagenRes = R.drawable.ej_alim,
        descripcion = "Pollo fresco de corral",
        categoria = CategoriaProducto.ALIMENTOS,
        subcategoria = "Carnes",
        evaluacion = 4.6f
    ),
    ClaseProductos(
        nombre = "Carne de vacuno",
        precio = 3500,
        imagenRes = R.drawable.ej_alim,
        descripcion = "Carne de vacuno premium",
        categoria = CategoriaProducto.ALIMENTOS,
        subcategoria = "Carnes",
        evaluacion = 4.7f
    ),

    // SALUD
    ClaseProductos(
        nombre = "Jabón",
        precio = 1200,
        imagenRes = R.drawable.ej_alim,
        descripcion = "Jabón líquido para higiene diaria",
        categoria = CategoriaProducto.SALUD,
        subcategoria = "Higiene",
        evaluacion = 4.3f
    ),
    ClaseProductos(
        nombre = "Cepillo dental",
        precio = 800,
        imagenRes = R.drawable.ej_alim,
        descripcion = "Cepillo dental suave",
        categoria = CategoriaProducto.SALUD,
        subcategoria = "Higiene",
        evaluacion = 4.5f
    ),
    ClaseProductos(
        nombre = "Shampoo",
        precio = 1500,
        imagenRes = R.drawable.ej_alim,
        descripcion = "Shampoo nutritivo para cabello",
        categoria = CategoriaProducto.SALUD,
        subcategoria = "Higiene",
        evaluacion = 4.2f
    ),
    ClaseProductos(
        nombre = "Vitamina C",
        precio = 2500,
        imagenRes = R.drawable.ej_alim,
        descripcion = "Suplemento de vitamina C",
        categoria = CategoriaProducto.SALUD,
        subcategoria = "Suplementos",
        evaluacion = 4.6f
    ),
    ClaseProductos(
        nombre = "Omega 3",
        precio = 3000,
        imagenRes = R.drawable.ej_alim,
        descripcion = "Suplemento de Omega 3",
        categoria = CategoriaProducto.SALUD,
        subcategoria = "Suplementos",
        evaluacion = 4.8f
    ),
    ClaseProductos(
        nombre = "Magnesio",
        precio = 2200,
        imagenRes = R.drawable.ej_alim,
        descripcion = "Suplemento de magnesio",
        categoria = CategoriaProducto.SALUD,
        subcategoria = "Suplementos",
        evaluacion = 4.4f
    ),
    ClaseProductos(
        nombre = "Paracetamol",
        precio = 1800,
        imagenRes = R.drawable.ej_alim,
        descripcion = "Analgésico y antipirético",
        categoria = CategoriaProducto.SALUD,
        subcategoria = "Medicamentos",
        evaluacion = 4.5f
    ),
    ClaseProductos(
        nombre = "Ibuprofeno",
        precio = 2000,
        imagenRes = R.drawable.ej_alim,
        descripcion = "Antiinflamatorio y analgésico",
        categoria = CategoriaProducto.SALUD,
        subcategoria = "Medicamentos",
        evaluacion = 4.3f
    ),
    ClaseProductos(
        nombre = "Amoxicilina",
        precio = 2500,
        imagenRes = R.drawable.ej_alim,
        descripcion = "Antibiótico recetado",
        categoria = CategoriaProducto.SALUD,
        subcategoria = "Medicamentos",
        evaluacion = 4.7f
    ),

    // MASCOTAS
    ClaseProductos(
        nombre = "Comida Gato",
        precio = 3000,
        imagenRes = R.drawable.ej_alim,
        descripcion = "Alimento balanceado para gatos",
        categoria = CategoriaProducto.MASCOTAS,
        subcategoria = "Gato",
        evaluacion = 4.6f
    ),
    ClaseProductos(
        nombre = "Juguete Gato",
        precio = 1200,
        imagenRes = R.drawable.ej_alim,
        descripcion = "Juguete interactivo para gatos",
        categoria = CategoriaProducto.MASCOTAS,
        subcategoria = "Gato",
        evaluacion = 4.4f
    ),
    ClaseProductos(
        nombre = "Arena Gato",
        precio = 2000,
        imagenRes = R.drawable.ej_alim,
        descripcion = "Arena higiénica para gatos",
        categoria = CategoriaProducto.MASCOTAS,
        subcategoria = "Gato",
        evaluacion = 4.5f
    ),
    ClaseProductos(
        nombre = "Comida Perro",
        precio = 3500,
        imagenRes = R.drawable.ej_alim,
        descripcion = "Alimento balanceado para perros",
        categoria = CategoriaProducto.MASCOTAS,
        subcategoria = "Perro",
        evaluacion = 4.7f
    ),
    ClaseProductos(
        nombre = "Collar Perro",
        precio = 1500,
        imagenRes = R.drawable.ej_alim,
        descripcion = "Collar ajustable para perros",
        categoria = CategoriaProducto.MASCOTAS,
        subcategoria = "Perro",
        evaluacion = 4.6f
    ),
    ClaseProductos(
        nombre = "Juguete Perro",
        precio = 1800,
        imagenRes = R.drawable.ej_alim,
        descripcion = "Juguete para morder",
        categoria = CategoriaProducto.MASCOTAS,
        subcategoria = "Perro",
        evaluacion = 4.5f
    ),

    // HOGAR
    ClaseProductos(
        nombre = "Silla de comedor",
        precio = 45000,
        imagenRes = R.drawable.ej_alim,
        descripcion = "Silla cómoda para comedor",
        categoria = CategoriaProducto.HOGAR,
        subcategoria = "Muebles",
        evaluacion = 4.6f
    ),
    ClaseProductos(
        nombre = "Mesa de centro",
        precio = 55000,
        imagenRes = R.drawable.ej_alim,
        descripcion = "Mesa de centro elegante",
        categoria = CategoriaProducto.HOGAR,
        subcategoria = "Muebles",
        evaluacion = 4.7f
    ),
    ClaseProductos(
        nombre = "Estantería",
        precio = 60000,
        imagenRes = R.drawable.ej_alim,
        descripcion = "Estantería de madera",
        categoria = CategoriaProducto.HOGAR,
        subcategoria = "Muebles",
        evaluacion = 4.5f
    ),
    ClaseProductos(
        nombre = "Taladro",
        precio = 25000,
        imagenRes = R.drawable.ej_alim,
        descripcion = "Taladro eléctrico",
        categoria = CategoriaProducto.HOGAR,
        subcategoria = "Herramientas",
        evaluacion = 4.8f
    ),
    ClaseProductos(
        nombre = "Destornillador set",
        precio = 12000,
        imagenRes = R.drawable.ej_alim,
        descripcion = "Juego de destornilladores",
        categoria = CategoriaProducto.HOGAR,
        subcategoria = "Herramientas",
        evaluacion = 4.6f
    ),
    ClaseProductos(
        nombre = "Sierra manual",
        precio = 15000,
        imagenRes = R.drawable.ej_alim,
        descripcion = "Sierra manual de carpintería",
        categoria = CategoriaProducto.HOGAR,
        subcategoria = "Herramientas",
        evaluacion = 4.4f
    ),
    // JARDÍN
    ClaseProductos(
        nombre = "Planta interior",
        precio = 8000,
        imagenRes = R.drawable.ej_alim,
        descripcion = "Planta para interiores",
        categoria = CategoriaProducto.JARDIN,
        subcategoria = "Plantas",
        evaluacion = 4.7f
    ),
    ClaseProductos(
        nombre = "Planta exterior",
        precio = 10000,
        imagenRes = R.drawable.ej_alim,
        descripcion = "Planta para exteriores",
        categoria = CategoriaProducto.JARDIN,
        subcategoria = "Plantas",
        evaluacion = 4.6f
    ),
    ClaseProductos(
        nombre = "Suculenta",
        precio = 5000,
        imagenRes = R.drawable.ej_alim,
        descripcion = "Planta suculenta resistente",
        categoria = CategoriaProducto.JARDIN,
        subcategoria = "Plantas",
        evaluacion = 4.5f
    ),
    ClaseProductos(
        nombre = "Guantes de jardinería",
        precio = 3000,
        imagenRes = R.drawable.ej_alim,
        descripcion = "Guantes resistentes para jardinería",
        categoria = CategoriaProducto.JARDIN,
        subcategoria = "Accesorios",
        evaluacion = 4.6f
    ),
    ClaseProductos(
        nombre = "Manguera",
        precio = 7000,
        imagenRes = R.drawable.ej_alim,
        descripcion = "Manguera flexible para riego",
        categoria = CategoriaProducto.JARDIN,
        subcategoria = "Accesorios",
        evaluacion = 4.5f
    ),
    ClaseProductos(
        nombre = "Regadera",
        precio = 4500,
        imagenRes = R.drawable.ej_alim,
        descripcion = "Regadera plástica de 2L",
        categoria = CategoriaProducto.JARDIN,
        subcategoria = "Accesorios",
        evaluacion = 4.4f
    ),
    ClaseProductos(
        nombre = "Fertilizante orgánico",
        precio = 12000,
        imagenRes = R.drawable.ej_alim,
        descripcion = "Fertilizante natural",
        categoria = CategoriaProducto.JARDIN,
        subcategoria = "Fertilizantes",
        evaluacion = 4.7f
    ),
    ClaseProductos(
        nombre = "Fertilizante líquido",
        precio = 10000,
        imagenRes = R.drawable.ej_alim,
        descripcion = "Fertilizante líquido concentrado",
        categoria = CategoriaProducto.JARDIN,
        subcategoria = "Fertilizantes",
        evaluacion = 4.6f
    ),
    ClaseProductos(
        nombre = "Abono granular",
        precio = 8000,
        imagenRes = R.drawable.ej_alim,
        descripcion = "Abono en gránulos",
        categoria = CategoriaProducto.JARDIN,
        subcategoria = "Fertilizantes",
        evaluacion = 4.5f
    ),

    // BELLEZA
    ClaseProductos(
        nombre = "Labial",
        precio = 7000,
        imagenRes = R.drawable.ej_alim,
        descripcion = "Labial de larga duración",
        categoria = CategoriaProducto.BELLEZA,
        subcategoria = "Maquillaje",
        evaluacion = 4.8f
    ),
    ClaseProductos(
        nombre = "Base de maquillaje",
        precio = 12000,
        imagenRes = R.drawable.ej_alim,
        descripcion = "Base líquida de alta cobertura",
        categoria = CategoriaProducto.BELLEZA,
        subcategoria = "Maquillaje",
        evaluacion = 4.7f
    ),
    ClaseProductos(
        nombre = "Rubor",
        precio = 9000,
        imagenRes = R.drawable.ej_alim,
        descripcion = "Rubor en polvo",
        categoria = CategoriaProducto.BELLEZA,
        subcategoria = "Maquillaje",
        evaluacion = 4.5f
    ),
    ClaseProductos(
        nombre = "Perfume femenino",
        precio = 35000,
        imagenRes = R.drawable.ej_alim,
        descripcion = "Perfume para mujer",
        categoria = CategoriaProducto.BELLEZA,
        subcategoria = "Perfumería",
        evaluacion = 4.8f
    ),
    ClaseProductos(
        nombre = "Perfume masculino",
        precio = 37000,
        imagenRes = R.drawable.ej_alim,
        descripcion = "Perfume para hombre",
        categoria = CategoriaProducto.BELLEZA,
        subcategoria = "Perfumería",
        evaluacion = 4.7f
    ),
    ClaseProductos(
        nombre = "Crema corporal",
        precio = 15000,
        imagenRes = R.drawable.ej_alim,
        descripcion = "Crema hidratante corporal",
        categoria = CategoriaProducto.BELLEZA,
        subcategoria = "Perfumería",
        evaluacion = 4.6f
    ),

    // REGALOS
    ClaseProductos(
        nombre = "Cartera mujer",
        precio = 25000,
        imagenRes = R.drawable.ej_alim,
        descripcion = "Cartera elegante para mujer",
        categoria = CategoriaProducto.REGALOS,
        subcategoria = "Ella",
        evaluacion = 4.7f
    ),
    ClaseProductos(
        nombre = "Collar mujer",
        precio = 18000,
        imagenRes = R.drawable.ej_alim,
        descripcion = "Collar de moda",
        categoria = CategoriaProducto.REGALOS,
        subcategoria = "Ella",
        evaluacion = 4.6f
    ),
    ClaseProductos(
        nombre = "Set maquillaje",
        precio = 30000,
        imagenRes = R.drawable.ej_alim,
        descripcion = "Set completo de maquillaje",
        categoria = CategoriaProducto.REGALOS,
        subcategoria = "Ella",
        evaluacion = 4.8f
    ),
    ClaseProductos(
        nombre = "Reloj hombre",
        precio = 40000,
        imagenRes = R.drawable.ej_alim,
        descripcion = "Reloj clásico para hombre",
        categoria = CategoriaProducto.REGALOS,
        subcategoria = "Él",
        evaluacion = 4.7f
    ),
    ClaseProductos(
        nombre = "Cinturón hombre",
        precio = 15000,
        imagenRes = R.drawable.ej_alim,
        descripcion = "Cinturón de cuero",
        categoria = CategoriaProducto.REGALOS,
        subcategoria = "Él",
        evaluacion = 4.5f
    ),
    ClaseProductos(
        nombre = "Set afeitado",
        precio = 22000,
        imagenRes = R.drawable.ej_alim,
        descripcion = "Set de afeitado completo",
        categoria = CategoriaProducto.REGALOS,
        subcategoria = "Él",
        evaluacion = 4.6f
    ),
    ClaseProductos(
        nombre = "Muñeco niño",
        precio = 12000,
        imagenRes = R.drawable.ej_alim,
        descripcion = "Muñeco de juguete para niños",
        categoria = CategoriaProducto.REGALOS,
        subcategoria = "Niño",
        evaluacion = 4.6f
    ),
    ClaseProductos(
        nombre = "Libro infantil",
        precio = 8000,
        imagenRes = R.drawable.ej_alim,
        descripcion = "Libro educativo para niños",
        categoria = CategoriaProducto.REGALOS,
        subcategoria = "Niño",
        evaluacion = 4.5f
    ),
    ClaseProductos(
        nombre = "Set de construcción",
        precio = 15000,
        imagenRes = R.drawable.ej_alim,
        descripcion = "Set para construir juguetes",
        categoria = CategoriaProducto.REGALOS,
        subcategoria = "Niño",
        evaluacion = 4.7f
    ),
    ClaseProductos(
        nombre = "Muñeca niña",
        precio = 12000,
        imagenRes = R.drawable.ej_alim,
        descripcion = "Muñeca de juguete para niñas",
        categoria = CategoriaProducto.REGALOS,
        subcategoria = "Niña",
        evaluacion = 4.6f
    ),
    ClaseProductos(
        nombre = "Puzzle educativo",
        precio = 9000,
        imagenRes = R.drawable.ej_alim,
        descripcion = "Puzzle educativo para niñas",
        categoria = CategoriaProducto.REGALOS,
        subcategoria = "Niña",
        evaluacion = 4.5f
    ),
    ClaseProductos(
        nombre = "Set de arte",
        precio = 14000,
        imagenRes = R.drawable.ej_alim,
        descripcion = "Set de arte creativo para niñas",
        categoria = CategoriaProducto.REGALOS,
        subcategoria = "Niña",
        evaluacion = 4.7f
    ),
    // CUIDADOS
    ClaseProductos(
        nombre = "Masaje relajante",
        precio = 20000,
        imagenRes = R.drawable.ej_alim,
        descripcion = "Masaje para relajación",
        categoria = CategoriaProducto.CUIDADOS,
        subcategoria = "Terapias",
        evaluacion = 4.8f
    ),
    ClaseProductos(
        nombre = "Acupuntura",
        precio = 25000,
        imagenRes = R.drawable.ej_alim,
        descripcion = "Sesión de acupuntura",
        categoria = CategoriaProducto.CUIDADOS,
        subcategoria = "Terapias",
        evaluacion = 4.7f
    ),
    ClaseProductos(
        nombre = "Reflexología",
        precio = 18000,
        imagenRes = R.drawable.ej_alim,
        descripcion = "Terapia de reflexología",
        categoria = CategoriaProducto.CUIDADOS,
        subcategoria = "Terapias",
        evaluacion = 4.6f
    ),
    ClaseProductos(
        nombre = "Limpieza facial",
        precio = 12000,
        imagenRes = R.drawable.ej_alim,
        descripcion = "Limpieza facial profesional",
        categoria = CategoriaProducto.CUIDADOS,
        subcategoria = "Servicios",
        evaluacion = 4.7f
    ),
    ClaseProductos(
        nombre = "Depilación",
        precio = 15000,
        imagenRes = R.drawable.ej_alim,
        descripcion = "Servicio de depilación",
        categoria = CategoriaProducto.CUIDADOS,
        subcategoria = "Servicios",
        evaluacion = 4.6f
    ),
    ClaseProductos(
        nombre = "Manicure",
        precio = 10000,
        imagenRes = R.drawable.ej_alim,
        descripcion = "Servicio de manicure",
        categoria = CategoriaProducto.CUIDADOS,
        subcategoria = "Servicios",
        evaluacion = 4.5f
    ),

    // VIAJES
    ClaseProductos(
        nombre = "Tour Santiago",
        precio = 50000,
        imagenRes = R.drawable.ej_alim,
        descripcion = "Tour por Santiago",
        categoria = CategoriaProducto.VIAJES,
        subcategoria = "Nacional",
        evaluacion = 4.8f
    ),
    ClaseProductos(
        nombre = "Tour Valparaíso",
        precio = 45000,
        imagenRes = R.drawable.ej_alim,
        descripcion = "Tour por Valparaíso",
        categoria = CategoriaProducto.VIAJES,
        subcategoria = "Nacional",
        evaluacion = 4.7f
    ),
    ClaseProductos(
        nombre = "Excursión Patagonia",
        precio = 80000,
        imagenRes = R.drawable.ej_alim,
        descripcion = "Excursión a la Patagonia",
        categoria = CategoriaProducto.VIAJES,
        subcategoria = "Nacional",
        evaluacion = 4.9f
    ),
    ClaseProductos(
        nombre = "Tour París",
        precio = 120000,
        imagenRes = R.drawable.ej_alim,
        descripcion = "Tour por París",
        categoria = CategoriaProducto.VIAJES,
        subcategoria = "Internacional",
        evaluacion = 4.9f
    ),
    ClaseProductos(
        nombre = "Tour Roma",
        precio = 115000,
        imagenRes = R.drawable.ej_alim,
        descripcion = "Tour por Roma",
        categoria = CategoriaProducto.VIAJES,
        subcategoria = "Internacional",
        evaluacion = 4.8f
    ),
    ClaseProductos(
        nombre = "Tour Nueva York",
        precio = 130000,
        imagenRes = R.drawable.ej_alim,
        descripcion = "Tour por Nueva York",
        categoria = CategoriaProducto.VIAJES,
        subcategoria = "Internacional",
        evaluacion = 5.0f
    ),

    // PANORAMAS
    ClaseProductos(
        nombre = "Picnic parque",
        precio = 15000,
        imagenRes = R.drawable.ej_alim,
        descripcion = "Picnic en el parque",
        categoria = CategoriaProducto.PANORAMAS,
        subcategoria = "Comidas",
        evaluacion = 4.7f
    ),
    ClaseProductos(
        nombre = "Asado familiar",
        precio = 25000,
        imagenRes = R.drawable.ej_alim,
        descripcion = "Asado para toda la familia",
        categoria = CategoriaProducto.PANORAMAS,
        subcategoria = "Comidas",
        evaluacion = 4.8f
    ),
    ClaseProductos(
        nombre = "Brunch amigos",
        precio = 20000,
        imagenRes = R.drawable.ej_alim,
        descripcion = "Brunch con amigos",
        categoria = CategoriaProducto.PANORAMAS,
        subcategoria = "Comidas",
        evaluacion = 4.6f
    ),
    ClaseProductos(
        nombre = "Caminata bosque",
        precio = 5000,
        imagenRes = R.drawable.ej_alim,
        descripcion = "Caminata por el bosque",
        categoria = CategoriaProducto.PANORAMAS,
        subcategoria = "Aire libre",
        evaluacion = 4.7f
    ),
    ClaseProductos(
        nombre = "Tour bicicleta",
        precio = 7000,
        imagenRes = R.drawable.ej_alim,
        descripcion = "Tour en bicicleta",
        categoria = CategoriaProducto.PANORAMAS,
        subcategoria = "Aire libre",
        evaluacion = 4.8f
    ),
    ClaseProductos(
        nombre = "Camping",
        precio = 12000,
        imagenRes = R.drawable.ej_alim,
        descripcion = "Camping al aire libre",
        categoria = CategoriaProducto.PANORAMAS,
        subcategoria = "Aire libre",
        evaluacion = 4.9f
    )



)