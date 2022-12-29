package com.ba.gclockclone.core.component

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.ba.gclockclone.core.navigation.BottomBarTabs

@Composable
fun GClockCloneBottomAppBar(
	selectedItem: BottomBarTabs,
	items: Array<BottomBarTabs>,
	onItemSelect: (BottomBarTabs) -> Unit,
) {
	NavigationBar {
		items.forEach {
			NavigationBarItem(
				selected = it == selectedItem,
				onClick = { onItemSelect(it) },
				icon = { Icon(imageVector = it.icon, contentDescription = it.name) },
				label = { Text(text = it.name) },
			)
		}
	}
}