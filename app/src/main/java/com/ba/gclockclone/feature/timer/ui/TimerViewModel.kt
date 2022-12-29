package com.ba.gclockclone.feature.timer.ui

import android.app.Application
import android.media.MediaPlayer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ba.gclockclone.R
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
	context: Application,
) : ViewModel() {

	private val mediaPlayer = MediaPlayer.create(context, R.raw.rooster)

	private val timerDuration = MutableStateFlow<Duration?>(null)
	private val baseDuration = MutableStateFlow(Duration.ZERO)

	private val _timerLabel = MutableStateFlow("")
	val timerLabel = _timerLabel.asStateFlow()

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

	val timerProgress = timerDuration.combine(baseDuration) { timer, base ->
		val res = timer?.minus(1.seconds)?.div(base)?.toFloat()
		res?.takeIf { it > 0f } ?: 0f
	}.stateIn(viewModelScope, SharingStarted.Eagerly, 1f)

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
					insert(0, "-")
				}
			}
		} ?: ""
	}.stateIn(viewModelScope, SharingStarted.Eagerly, "")

	val finished = timerDuration.map { duration ->
		duration == Duration.ZERO || duration?.isNegative() == true
	}.onEach { isFinished ->
		if (!mediaPlayer.isPlaying && isFinished) {
			mediaPlayer.start()
		}
	}.stateIn(viewModelScope, SharingStarted.Eagerly, false)

	private var remainingDuration = 0.seconds

	private var job: Job? = null

	fun startTimer() {
		timerDuration.update {
			hours.value.toInt().hours +
					minutes.value.toInt().minutes +
					seconds.value.toInt().seconds
		}
		baseDuration.update { timerDuration.value ?: 0.seconds }
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
		remainingDuration = 0.seconds
		timerDuration.update { null }
		mediaPlayer.stop()
		if (isPaused.value) {
			_isPaused.update { false }
		}
	}

	fun addMinute() {
		baseDuration.update { it + 1.minutes }
		timerDuration.update { duration ->
			duration?.takeIf { it.isPositive() }?.plus(1.minutes) ?: baseDuration.value
		}
	}

	fun pauseTimer() {
		if (timerDuration.value == 0.seconds || timerDuration.value?.isNegative() == true) {
			stopTimer()
			return
		}
		mediaPlayer.stop()
		job?.cancel()
		remainingDuration = timerDuration.value!!
		_isPaused.update { true }
	}

	fun resumeTimer() {
		timerDuration.update { remainingDuration }
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
