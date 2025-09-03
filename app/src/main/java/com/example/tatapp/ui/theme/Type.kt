package com.example.tatapp.ui.theme

import com.example.tatapp.R
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.sp

val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

val poppinsFontFamily = FontFamily(
    Font(
        googleFont = GoogleFont("Poppins"),
        fontProvider = provider,
    )
)

val h1 = TextStyle(
    fontFamily = poppinsFontFamily,
    fontWeight = FontWeight.Bold,
    fontSize = 34.sp,
    lineHeight = 40.sp,
    letterSpacing = 0.sp
)

val h2 = TextStyle(
    fontFamily = poppinsFontFamily,
    fontWeight = FontWeight.SemiBold,
    fontSize = 28.sp,
    lineHeight = 36.sp,
    letterSpacing = 0.sp
)

val h3 = TextStyle(
    fontFamily = poppinsFontFamily,
    fontWeight = FontWeight.Medium,
    fontSize = 22.sp,
    lineHeight = 28.sp,
    letterSpacing = 0.sp
)

// Default Material 3 typography values
val baseline = Typography()

val AppTypography = Typography(
    //displayLarge = baseline.displayLarge.copy(fontFamily = displayFontFamily),
    //displayMedium = baseline.displayMedium.copy(fontFamily = displayFontFamily),
    //displaySmall = baseline.displaySmall.copy(fontFamily = displayFontFamily),
    displayLarge = h1,
    displayMedium = h2,
    displaySmall = h3,
    headlineLarge = baseline.headlineLarge.copy(fontFamily = poppinsFontFamily),
    headlineMedium = baseline.headlineMedium.copy(fontFamily = poppinsFontFamily),
    headlineSmall = baseline.headlineSmall.copy(fontFamily = poppinsFontFamily),
    titleLarge = baseline.titleLarge.copy(fontFamily = poppinsFontFamily),
    titleMedium = baseline.titleMedium.copy(fontFamily = poppinsFontFamily),
    titleSmall = baseline.titleSmall.copy(fontFamily = poppinsFontFamily),
    bodyLarge = baseline.bodyLarge.copy(fontFamily = poppinsFontFamily),
    bodyMedium = baseline.bodyMedium.copy(fontFamily = poppinsFontFamily),
    bodySmall = baseline.bodySmall.copy(fontFamily = poppinsFontFamily),
    labelLarge = baseline.labelLarge.copy(fontFamily = poppinsFontFamily),
    labelMedium = baseline.labelMedium.copy(fontFamily = poppinsFontFamily),
    labelSmall = baseline.labelSmall.copy(fontFamily = poppinsFontFamily),
)