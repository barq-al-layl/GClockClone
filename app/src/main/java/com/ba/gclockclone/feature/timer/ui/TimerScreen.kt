package com.ba.gclockclone.feature.timer.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun TimerScreen(
	viewModel: TimerViewModel = hiltViewModel(),
) {
	val seconds by viewModel.seconds.collectAsState()
	val minutes by viewModel.minutes.collectAsState()
	val hours by viewModel.hours.collectAsState()
	val isAbleToStart by viewModel.isAbleToStart.collectAsState()
	val label by viewModel.timerLabel.collectAsState()
	val formattedDuration by viewModel.formattedDuration.collectAsState()
	val isPaused by viewModel.isPaused.collectAsState()
	val timerProgress by viewModel.timerProgress.collectAsState()
	val isFinished by viewModel.finished.collectAsState()

	Scaffold(
		floatingActionButtonPosition = FabPosition.Center,
		floatingActionButton = {
			AnimatedVisibility(
				visible = isAbleToStart,
				enter = scaleIn(
					animationSpec = tween(durationMillis = 150, easing = LinearEasing)
				),
				exit = if (formattedDuration.isEmpty()) scaleOut(
					animationSpec = tween(durationMillis = 150, easing = LinearEasing),
				)
				else {
					slideOutVertically(
						targetOffsetY = { it },
						animationSpec = tween(durationMillis = 150),
					) + fadeOut(animationSpec = tween(durationMillis = 150))
				},
			) {
				FloatingActionButton(
					modifier = Modifier
						.width(100.dp)
						.aspectRatio(1f),
					shape = CircleShape,
					containerColor = MaterialTheme.colorScheme.primary,
					onClick = viewModel::startTimer,
				) {
					Icon(
						imageVector = Icons.Default.PlayArrow,
						contentDescription = null,
					)
				}
			}
		}
	) { innerPadding ->
		AnimatedVisibility(
			visible = formattedDuration.isNotEmpty(),
			enter = slideInVertically(
				animationSpec = tween(delayMillis = 150),
				initialOffsetY = { -it / 2 },
			) + fadeIn(
				animationSpec = tween(delayMillis = 150),
			),
			exit = slideOutVertically(
				animationSpec = tween(durationMillis = 150),
				targetOffsetY = { -it / 2 },
			) + fadeOut(
				animationSpec = tween(durationMillis = 150),
			),
			modifier = Modifier
				.fillMaxSize()
				.padding(innerPadding)
				.padding(start = 16.dp, top = 24.dp, end = 16.dp, bottom = 140.dp),
		) {
			ActiveTimerCard(
				modifier = Modifier.fillMaxWidth(),
				label = label,
				time = formattedDuration,
				isPaused = isPaused,
				isFinished = isFinished,
				progress = timerProgress,
				onStop = viewModel::stopTimer,
				onAddMinute = viewModel::addMinute,
				onPause = if (isPaused) viewModel::resumeTimer
				else viewModel::pauseTimer,
			)
		}
		AnimatedVisibility(
			visible = formattedDuration.isEmpty(),
			enter = slideInVertically(
				initialOffsetY = { it / 2 },
				animationSpec = tween(delayMillis = 150),
			) + fadeIn(
				animationSpec = tween(delayMillis = 150),
			),
			exit = slideOutVertically(
				animationSpec = tween(durationMillis = 150),
				targetOffsetY = { it / 2 },
			) + fadeOut(
				animationSpec = tween(durationMillis = 150),
			),
			modifier = Modifier
				.fillMaxSize()
				.padding(innerPadding)
				.padding(top = 24.dp, bottom = 140.dp),
		) {
			TimerInput(
				modifier = Modifier.fillMaxWidth(),
				seconds = seconds,
				minutes = minutes,
				hours = hours,
				onNumberClick = viewModel::keypadClick,
			)
		}
	}
}
