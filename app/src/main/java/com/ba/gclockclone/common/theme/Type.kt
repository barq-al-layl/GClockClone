package com.ba.gclockclone.common.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.ba.gclockclone.R

val Roboto = FontFamily(
	Font(R.font.roboto_regular),
	Font(R.font.roboto_thin, weight = FontWeight.Thin),
	Font(R.font.roboto_light, weight = FontWeight.Light),
	Font(R.font.roboto_medium, weight = FontWeight.Medium),
	Font(R.font.roboto_bold, weight = FontWeight.Bold),
	Font(R.font.roboto_black, weight = FontWeight.Black),
)

private val default = Typography()

val Typography = Typography(
	displayLarge = default.displayLarge.copy(fontFamily = Roboto),
	displayMedium = default.displayMedium.copy(fontFamily = Roboto),
	displaySmall = default.displaySmall.copy(fontFamily = Roboto),
	headlineLarge = default.headlineLarge.copy(fontFamily = Roboto),
	headlineMedium = default.headlineMedium.copy(fontFamily = Roboto),
	headlineSmall = default.headlineSmall.copy(fontFamily = Roboto),
	titleLarge = default.titleLarge.copy(fontFamily = Roboto),
	titleMedium = default.titleMedium.copy(fontFamily = Roboto),
	titleSmall = default.titleSmall.copy(fontFamily = Roboto),
	bodyLarge = default.bodyLarge.copy(fontFamily = Roboto),
	bodyMedium = default.bodyMedium.copy(fontFamily = Roboto),
	bodySmall = default.bodySmall.copy(fontFamily = Roboto),
	labelLarge = default.labelLarge.copy(fontFamily = Roboto),
	labelMedium = default.labelMedium.copy(fontFamily = Roboto),
	labelSmall = default.labelSmall.copy(fontFamily = Roboto),
)

