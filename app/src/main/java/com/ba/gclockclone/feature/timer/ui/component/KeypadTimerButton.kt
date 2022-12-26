package com.ba.gclockclone.feature.timer.ui.component

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun KeypadTimerButton(
	label: String,
	onClick: (String) -> Unit,
) {
	ElevatedButton(
		onClick = { onClick(label) },
		modifier = Modifier
			.width(100.dp)
			.aspectRatio(1f),
		shape = CircleShape,
		colors = ButtonDefaults.elevatedButtonColors(
			contentColor = MaterialTheme.colorScheme.onBackground
		)
	) {
		Text(
			text = label,
			fontSize = 32.sp,
		)
	}
}
