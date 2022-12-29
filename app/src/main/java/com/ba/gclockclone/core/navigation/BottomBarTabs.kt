package com.ba.gclockclone.core.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.material.icons.filled.HourglassBottom
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material.icons.outlined.WatchLater
import androidx.compose.ui.graphics.vector.ImageVector

enum class BottomBarTabs(
	val route: String,
	val icon: ImageVector,
) {
	Alarm("/alarm", Icons.Default.Alarm),
	Clock("/clock", Icons.Outlined.WatchLater),
	Timer("/timer", Icons.Default.HourglassBottom),
	Stopwatch("/stopwatch", Icons.Outlined.Timer),
	Bedtime("/bedtime", Icons.Default.Bedtime),
}