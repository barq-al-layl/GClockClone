package com.ba.gclockclone.feature.timer.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Backspace
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun TimerKeypad(
	modifier: Modifier = Modifier,
	horizontalSpace: Dp = 4.dp,
	verticalSpace: Dp = 4.dp,
	onKeyClick: (String) -> Unit,
) {
	Column(
		modifier = modifier,
		verticalArrangement = Arrangement.spacedBy(verticalSpace, Alignment.Bottom),
	) {
		Row(
			horizontalArrangement = Arrangement.spacedBy(horizontalSpace),
		) {
			KeypadTimerButton(label = "1", onClick = onKeyClick)
			KeypadTimerButton(label = "2", onClick = onKeyClick)
			KeypadTimerButton(label = "3", onClick = onKeyClick)
		}
		Row(
			horizontalArrangement = Arrangement.spacedBy(horizontalSpace),
		) {
			KeypadTimerButton(label = "4", onClick = onKeyClick)
			KeypadTimerButton(label = "5", onClick = onKeyClick)
			KeypadTimerButton(label = "6", onClick = onKeyClick)
		}
		Row(
			horizontalArrangement = Arrangement.spacedBy(horizontalSpace),
		) {
			KeypadTimerButton(label = "7", onClick = onKeyClick)
			KeypadTimerButton(label = "8", onClick = onKeyClick)
			KeypadTimerButton(label = "9", onClick = onKeyClick)
		}
		Row(
			horizontalArrangement = Arrangement.spacedBy(horizontalSpace),
		) {
			KeypadTimerButton(label = "00", onClick = onKeyClick)
			KeypadTimerButton(label = "0", onClick = onKeyClick)
			Button(
				onClick = { onKeyClick("") },
				modifier = Modifier
					.width(100.dp)
					.aspectRatio(1f),
				shape = CircleShape,
				colors = ButtonDefaults.buttonColors(
					containerColor = MaterialTheme.colorScheme.secondaryContainer,
					contentColor = MaterialTheme.colorScheme.onBackground
				),
			) {
				Icon(
					imageVector = Icons.Outlined.Backspace,
					contentDescription = null,
				)
			}
		}
	}
}