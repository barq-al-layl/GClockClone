package com.ba.gclockclone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ba.gclockclone.core.AppContent
import com.ba.gclockclone.core.theme.GClockCloneTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			GClockCloneTheme {
				AppContent()
			}
		}
	}
}


