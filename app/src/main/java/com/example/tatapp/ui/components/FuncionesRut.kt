package com.example.tatapp.ui.components

//Función para normalizar input, retorna (numero,dv) o null en caso contrario
fun parseRut(input: String): Pair<String, String>? {
    if (input.isBlank()) return null
    val clean = input.replace(".", "").replace(" ", "").lowercase()

    val withDash = if ("-" in clean) clean else {
        if (clean.length < 2) return null
        clean.dropLast(1) + "-" + clean.takeLast(1)
    }

    val parts = withDash.split("-")
    if (parts.size != 2) return null

    val numero = parts[0]
    val dv = parts[1]
    if (numero.isEmpty() || !numero.all { it.isDigit() }) return null
    if (!dv.matches(Regex("^[0-9k]$"))) return null

    return numero to dv
}

// Función calcula digito verificador tarea 1
fun calcularDigitoVerificador(rut: String): String {
    var factor = 2
    var suma = 0
    var i = rut.length - 1
    while (i >= 0) {
        val digito = rut[i].toString().toInt()
        suma += digito * factor
        factor = if (factor == 7) 2 else factor + 1
        i--
    }
    val resto = 11 - (suma % 11)
    return when (resto) {
        11 -> "0"
        10 -> "K"
        else -> resto.toString()
    }
}

//Función que valida rut completo con el calculo
fun esRutValidoConFuncion(input: String): Boolean {
    val parsed = parseRut(input) ?: return false
    val (numero, dvIngresado) = parsed
    val dvEsperado = calcularDigitoVerificador(numero).lowercase()
    return dvIngresado.lowercase() == dvEsperado
}

//Función que formatea Rut a 12.2345.678-5
fun formatearRUT(input: String): String {
    val parsed = parseRut(input) ?: return input
    val (numero,dv) = parsed
    val conPuntos = numero.reversed().chunked(3).joinToString(".").reversed()
    return "$conPuntos-$dv".uppercase()
}

// Pequeña ayuda para saber si el input YA está “completo” (tiene número + DV)
fun rutCompleto(input: String): Boolean {
    val clean = input.replace(".", "").replace(" ", "").lowercase()
    val withDash = if ("-" in clean) clean else {
        if (clean.length < 2) return false
        clean.dropLast(1) + "-" + clean.takeLast(1)
    }
    val parts = withDash.split("-")
    if (parts.size != 2) return false
    val numero = parts[0]
    val dv = parts[1]
    // RUTs reales suelen tener 7 u 8 dígitos antes del DV
    return numero.length in 7..8 && dv.length == 1 && numero.all { it.isDigit() }
}