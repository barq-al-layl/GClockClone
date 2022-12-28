package com.ba.gclockclone.feature.timer.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ActiveTimerCard(
	modifier: Modifier = Modifier,
	label: String,
	time: String,
	isPaused: Boolean,
	isFinished: Boolean,
	progress: Float,
	onStop: () -> Unit,
	onAddMinute: () -> Unit,
	onPause: () -> Unit,
) {
	val progressIndicator = remember { Animatable(initialValue = 1f) }
	val indicatorColor = MaterialTheme.colorScheme.primary
	val indicatorBackground = MaterialTheme.colorScheme.secondaryContainer

	LaunchedEffect(progress) {
		progressIndicator.animateTo(
			targetValue = progress,
			animationSpec = tween(durationMillis = 1000, easing = LinearEasing),
		)
	}

	ElevatedCard(
		modifier = modifier,
		shape = MaterialTheme.shapes.extraLarge,
		colors = CardDefaults.elevatedCardColors(
			containerColor = if (isFinished) MaterialTheme.colorScheme.primary
			else MaterialTheme.colorScheme.surface
		),
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
				if (!isFinished) {
					IconButton(
						onClick = onStop,
						colors = IconButtonDefaults.iconButtonColors(
							containerColor = MaterialTheme.colorScheme.secondaryContainer,
						),
					) {
						Icon(imageVector = Icons.Default.Close, contentDescription = null)
					}
				}
			}
			Box(
				modifier = Modifier
					.size(300.dp)
					.drawBehind {
						drawCircle(
							color = indicatorBackground,
							style = Stroke(width = 10.dp.toPx()),
						)
						if (!isFinished) {
							drawArc(
								color = indicatorColor,
								startAngle = 270f,
								sweepAngle = progressIndicator.value * 360f,
								useCenter = false,
								size = size,
								style = Stroke(
									width = 10.dp.toPx(),
									cap = StrokeCap.Round,
								),
							)
						}
					},
				contentAlignment = Alignment.Center,
			) {
				Text(
					text = time,
					fontSize = 48.sp,
				)
			}
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
						containerColor = if (isFinished) MaterialTheme.colorScheme.onTertiaryContainer
						else MaterialTheme.colorScheme.tertiary,
					),
				) {
					Icon(
						imageVector = when {
							isFinished -> Icons.Default.Stop
							isPaused -> Icons.Default.PlayArrow
							else -> Icons.Default.Pause
						},
						contentDescription = null,
					)
				}
			}
		}
	}
}