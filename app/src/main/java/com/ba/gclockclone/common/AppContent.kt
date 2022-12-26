package com.ba.gclockclone.common

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.ba.gclockclone.common.component.GClockCloneBottomAppBar
import com.ba.gclockclone.common.component.GClockTopAppBar
import com.ba.gclockclone.common.navigation.BottomBarTabs
import com.ba.gclockclone.common.navigation.GClockNavHost

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppContent() {
	val navController = rememberNavController()
	var selectedItem by remember { mutableStateOf(BottomBarTabs.Alarm) }

	Scaffold(
		topBar = {
			GClockTopAppBar(title = selectedItem.name)
		},
		bottomBar = {
			GClockCloneBottomAppBar(
				selectedItem = selectedItem,
				items = BottomBarTabs.values(),
				onItemSelect = {
					if (it != selectedItem) {
						navController.navigate(it.route) {
							popUpTo(0) { saveState = true }
							launchSingleTop = true
							restoreState = true
						}
						selectedItem = it
					}
				},
			)
		},
	) { innerPadding ->
		GClockNavHost(
			navController = navController,
			modifier = Modifier.padding(innerPadding),
		)
	}
}