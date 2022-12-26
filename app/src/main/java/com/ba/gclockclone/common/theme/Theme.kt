package com.ba.gclockclone.common.theme

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
	primary = LightSkyBlue,
	background = EerieBlack,
	surface = CharlestonGreen,
)

@SuppressLint("ObsoleteSdkInt")
@Composable
fun GClockCloneTheme(
	content: @Composable () -> Unit,
) {
	val colorScheme = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
		val context = LocalContext.current
		dynamicDarkColorScheme(context)
	} else {
		DarkColorScheme
	}

	val view = LocalView.current
	if (!view.isInEditMode) {
		SideEffect {
			val window = (view.context as Activity).window
			window.statusBarColor = colorScheme.background.toArgb()
			WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
		}
	}

	MaterialTheme(
		colorScheme = colorScheme,
		typography = Typography,
		content = content,
	)
}