package com.example.sudokusolver.presentation.main_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sudokusolver.presentation.ui.theme.FadedOrange

@Composable
fun NumberBox(
    modifier: Modifier = Modifier,
    number: Int,
    onClick: () -> Unit
) {
    Box(modifier = modifier
        .clip(CircleShape)
        .background(FadedOrange)
        .clickable { onClick() }
        .padding(12.dp)
    ){
        Text(
            text = number.toString(),
            fontWeight = FontWeight.SemiBold,
            color = Color.White
        )
    }
}

@Preview
@Composable
private fun NumberBoxPreview() {
    NumberBox(number = 2){}
}