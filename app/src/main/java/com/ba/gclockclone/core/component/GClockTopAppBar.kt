package com.ba.gclockclone.core.component

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun GClockTopAppBar(title: String) {
	var menuExpanded by remember {
		mutableStateOf(false)
	}
	TopAppBar(
		title = {
			Text(text = title)
		},
		actions = {
			Box {
				IconButton(onClick = { menuExpanded = true }) {
					Icon(
						imageVector = Icons.Default.MoreVert,
						contentDescription = "More options",
					)
				}
				DropdownMenu(
					expanded = menuExpanded,
					onDismissRequest = { menuExpanded = false },
					offset = DpOffset(x = 12.dp, y = 0.dp),
					properties = PopupProperties(usePlatformDefaultWidth = true),
				) {
					DropDownMenuItems.values().forEach {
						DropdownMenuItem(
							text = { Text(text = it.title) },
							onClick = { },
						)
					}
				}

			}
		},
		colors = TopAppBarDefaults.topAppBarColors(
			containerColor = Color.Transparent,
		)
	)
}

enum class DropDownMenuItems(val title: String) {
	ScreenSaver("Screen saver"),
	Settings("Settings"),
	SendFeedback("Send feedback"),
	Help("Help"),
}