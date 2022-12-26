package com.ba.gclockclone.feature.timer.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ActiveTimerCard(
	modifier: Modifier = Modifier,
	label: String,
	time: String,
	isPaused: Boolean,
	onStop: () -> Unit,
	onAddMinute: () -> Unit,
	onPause: () -> Unit,
) {
	ElevatedCard(
		modifier = modifier,
		shape = MaterialTheme.shapes.extraLarge,
	) {
		Column(
			modifier = Modifier
				.fillMaxHeight()
				.padding(start = 24.dp, top = 16.dp, end = 24.dp, bottom = 32.dp),
			verticalArrangement = Arrangement.SpaceBetween,
			horizontalAlignment = Alignment.CenterHorizontally,
		) {
			Row(
				modifier = Modifier.fillMaxWidth(),
				horizontalArrangement = Arrangement.SpaceBetween,
			) {
				Text(
					text = "$label Timer",
					fontSize = 24.sp,
					fontWeight = FontWeight.W500,
				)
				IconButton(
					onClick = onStop,
					colors = IconButtonDefaults.iconButtonColors(
						containerColor = MaterialTheme.colorScheme.secondaryContainer,
					),
				) {
					Icon(imageVector = Icons.Default.Close, contentDescription = null)
				}
			}
			Text(
				text = time,
				fontSize = 48.sp,
			)
			Row(
				horizontalArrangement = Arrangement.spacedBy(16.dp),
			) {
				Button(
					modifier = Modifier.size(width = 140.dp, height = 80.dp),
					onClick = onAddMinute,
					colors = ButtonDefaults.buttonColors(
						containerColor = MaterialTheme.colorScheme.secondaryContainer
					)
				) {
					Text(
						text = "+1:00",
						color = MaterialTheme.colorScheme.onBackground,
						fontSize = 28.sp,
						fontWeight = FontWeight.Light
					)
				}
				Button(
					modifier = Modifier.size(width = 140.dp, height = 80.dp),
					onClick = onPause,
					colors = ButtonDefaults.buttonColors(
						containerColor = MaterialTheme.colorScheme.tertiary,
					),
				) {
					Icon(
						imageVector = if (isPaused) Icons.Default.PlayArrow
						else Icons.Default.Pause,
						contentDescription = null,
					)
				}
			}
		}
	}
}