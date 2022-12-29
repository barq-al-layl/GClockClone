package com.ba.gclockclone.core.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ba.gclockclone.feature.timer.ui.TimerScreen

@Composable
fun GClockNavHost(navController: NavHostController, modifier: Modifier) {
	NavHost(
		navController = navController,
		startDestination = BottomBarTabs.Timer.route,
		modifier = modifier
			.fillMaxSize()
			.background(MaterialTheme.colorScheme.background),
	) {
		composable(BottomBarTabs.Alarm.route) {

		}
		composable(BottomBarTabs.Clock.route) {

		}
		composable(BottomBarTabs.Timer.route) {
			TimerScreen()
		}
		composable(BottomBarTabs.Stopwatch.route) {

		}
		composable(BottomBarTabs.Bedtime.route) {

		}
	}
}