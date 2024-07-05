package com.example.sudokusolver.presentation.main_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun NumberBox(
    modifier: Modifier = Modifier,
    number: Int
) {
    Box(modifier = modifier
        .clip(CircleShape)
        .background(Color.Magenta)
        .padding(12.dp)
    ){
        Text(text = number.toString())
    }
}