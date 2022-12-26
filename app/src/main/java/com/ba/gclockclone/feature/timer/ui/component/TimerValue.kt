package com.ba.gclockclone.feature.timer.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TimerValue(
	seconds: String,
	minutes: String,
	hours: String,
	modifier: Modifier = Modifier,
) {
	Row(
		modifier = modifier,
		horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
		verticalAlignment = Alignment.CenterVertically,
	) {
		TimerValueItem(
			value = hours,
			unit = "h",
			isActive = hours != "00",
		)
		TimerValueItem(
			value = minutes,
			unit = "m",
			isActive = minutes != "00" || hours != "00",
		)
		TimerValueItem(
			value = seconds,
			unit = "s",
			isActive = seconds != "00" || minutes != "00" || hours != "00",
		)
	}
}

@Composable
private fun TimerValueItem(
	value: String,
	unit: String,
	isActive: Boolean,
) {
	Row(
		horizontalArrangement = Arrangement.spacedBy(1.dp),
	) {
		Text(
			text = value,
			modifier = Modifier.alignByBaseline(),
			fontSize = 48.sp,
			color = MaterialTheme.colorScheme.primary.takeIf { isActive }
				?: MaterialTheme.colorScheme.onSurface.copy(alpha = .6f)
		)
		Text(
			text = unit,
			modifier = Modifier.alignByBaseline(),
			fontSize = 22.sp,
			color = MaterialTheme.colorScheme.primary.takeIf { isActive }
				?: MaterialTheme.colorScheme.onSurface.copy(alpha = .6f)
		)
	}
}