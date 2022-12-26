package com.ba.gclockclone.feature.timer.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.ba.gclockclone.feature.timer.ui.component.TimerKeypad
import com.ba.gclockclone.feature.timer.ui.component.TimerValue

@Composable
fun TimerInput(
	modifier: Modifier = Modifier,
	seconds: String,
	minutes: String,
	hours: String,
	onNumberClick: (String) -> Unit,
) {
	Column(
		modifier = modifier,
		verticalArrangement = Arrangement.Center,
		horizontalAlignment = Alignment.CenterHorizontally,
	) {
		TimerValue(
			seconds = seconds,
			minutes = minutes,
			hours = hours,
		)
		TimerKeypad(
			modifier = Modifier.weight(5f),
			onKeyClick = onNumberClick,
		)
	}
}