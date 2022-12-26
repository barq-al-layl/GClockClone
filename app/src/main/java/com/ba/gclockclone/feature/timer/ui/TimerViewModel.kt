package com.ba.gclockclone.feature.timer.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class TimerViewModel @Inject constructor(

) : ViewModel() {

	private val timerString = MutableStateFlow("0".repeat(6))

	val seconds = timerString.map {
		it.takeLast(2)
	}.stateIn(viewModelScope, SharingStarted.Eagerly, "00")

	val minutes = timerString.map {
		it.substring(2, 4)
	}.stateIn(viewModelScope, SharingStarted.Eagerly, "00")

	val hours = timerString.map {
		it.take(2)
	}.stateIn(viewModelScope, SharingStarted.Eagerly, "00")

	private val _isPaused = MutableStateFlow(false)
	val isPaused = _isPaused.asStateFlow()

	// determines if the user can start the new timer or not
	val isAbleToStart = timerString.map { timer ->
		timer.any { it != '0' }
	}.combine(isPaused) { isNotBlank, paused ->
		isNotBlank && !paused
	}.stateIn(viewModelScope, SharingStarted.Eagerly, false)

	private val timerDuration = MutableStateFlow<Duration?>(null)

	private val _timerLabel = MutableStateFlow("")
	val timerLabel = _timerLabel.asStateFlow()

	val formattedDuration = timerDuration.map {
		timerDuration.value?.absoluteValue?.toComponents { hours, minutes, seconds, _ ->
			buildString {
				if (hours != 0L) {
					append(hours)
					append(':')
				}
				if (isNotEmpty() || minutes != 0) {
					if (isNotEmpty() && minutes < 10) append(0)
					append(minutes)
					append(':')
				}
				if (isNotEmpty() && seconds < 10) append(0)
				append(seconds)
				if (timerDuration.value?.isNegative() == true) {
					insert(0, "- ")
				}
			}
		} ?: ""
	}.stateIn(viewModelScope, SharingStarted.Eagerly, "")

	private var job: Job? = null

	fun startTimer() {
		timerDuration.update {
			hours.value.toInt().hours +
					minutes.value.toInt().minutes +
					seconds.value.toInt().seconds
		}
		timerString.update { "0".repeat(6) }
		_timerLabel.update {
			timerDuration.value?.toComponents { hours, minutes, seconds, _ ->
				buildString {
					if (hours != 0L) {
						append(hours)
						append("h ")
					}
					if (minutes != 0) {
						append(minutes)
						append("m ")
					}
					append(seconds)
					append("s")
				}
			} ?: ""
		}
		val oneSecond = 1.seconds
		job?.cancel()
		job = viewModelScope.launch {
			while (true) {
				delay(oneSecond)
				timerDuration.update { duration ->
					duration?.let { it - oneSecond } ?: return@launch
				}
			}
		}
	}

	fun stopTimer() {
		job?.cancel()
		job = null
		timerDuration.update { null }
	}

	fun addMinute() {
		timerDuration.update { duration ->
			(duration?.takeIf { it.isPositive() } ?: Duration.parse(timerLabel.value)) + 1.minutes
		}
	}

	fun pauseTimer() {
		if (timerDuration.value?.isNegative() == true) {
			stopTimer()
			return
		}
		timerString.update {
			job?.cancel()
			job = null
			timerDuration.value?.toComponents { hours, minutes, seconds, _ ->
				buildString {
					if (hours < 10) append("0")
					append(hours)
					if (minutes < 10) append("0")
					append(minutes)
					if (seconds < 10) append("0")
					append(seconds)
				}
			} ?: "0".repeat(6)
		}
		_isPaused.update { true }
	}

	fun resumeTimer() {
		timerDuration.update {
			hours.value.toInt().hours +
					minutes.value.toInt().minutes +
					seconds.value.toInt().seconds
		}
		timerString.update { "0".repeat(6) }
		val oneSecond = 1.seconds
		job?.cancel()
		job = viewModelScope.launch {
			while (true) {
				delay(oneSecond)
				timerDuration.update { duration ->
					duration?.let { it - oneSecond } ?: return@launch
				}
			}
		}
		_isPaused.update { false }
	}

	fun keypadClick(value: String) {
		timerString.update { timer ->
			val timerIntValue = timer.toInt()
			when {
				value.isEmpty() && timerIntValue != 0 -> {
					'0' + timer.dropLast(1)
				}
				value == "00" && timerIntValue != 0 -> {
					val zerosToAdd = timer.takeWhile { it == '0' }.length.takeIf { it < 2 } ?: 2
					timer.drop(zerosToAdd) + "0".repeat(zerosToAdd)
				}
				timer.first() == '0' && value.isNotEmpty() && value != "00" -> {
					timer.drop(1) + value
				}
				else -> timer
			}
		}
	}
}
