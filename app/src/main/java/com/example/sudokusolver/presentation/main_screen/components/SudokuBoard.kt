package com.example.sudokusolver.presentation.main_screen.components

import android.graphics.Paint
import android.graphics.Rect
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sudokusolver.common.Border
import com.example.sudokusolver.common.border
import com.example.sudokusolver.presentation.ui.theme.LightGreen

@Composable
fun SudokuBoard(
    modifier: Modifier = Modifier,
    grid: MutableList<MutableList<MutableState<String>>>,
    isCellNew: MutableList<MutableList<MutableState<Boolean>>>,
    onCellClick: (Int, Int) -> Unit,
    selectedCell: Pair<Int, Int>
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center
    ) {
        for (i in 0 until 9) {
            key(i) {
                Row {
                    for (j in 0 until 9) {
                        key(j) {
                            SudokuCell(
                                modifier = Modifier
                                    .weight(1f),
                                value = grid[i][j].value,
                                isNew = isCellNew[i][j].value,
                                isSelected = i == selectedCell.first && j == selectedCell.second,
                                onClick = { onCellClick(i, j) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SudokuCell(
    modifier: Modifier = Modifier,
    value: String,
    isNew: Boolean,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (isSelected) Color.LightGray else Color.White
    val textColor = if (isNew) LightGreen else Color.Black

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(40.dp)
            .background(backgroundColor)
            .border(
                start = Border(1.dp, Color.Black),
                top = Border(1.dp, Color.Black),
                end = Border(1.dp, Color.Black),
                bottom = Border(1.dp, Color.Black)
            )
            .clickable { onClick() }
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            if (value.isNotEmpty()) {
                val textPaint = Paint().apply {
                    color = textColor.toArgb()
                    textSize = 40f
                    textAlign = Paint.Align.CENTER
                }

                val textBounds = Rect()
                textPaint.getTextBounds(value, 0, value.length, textBounds)

                val x = size.width / 2
                val y = size.height / 2 + textBounds.height() / 2 - textBounds.bottom

                drawContext.canvas.nativeCanvas.drawText(value, x, y, textPaint)
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun SudokuBoardPreview() {
    SudokuBoard(
        grid = mutableListOf(*Array(9) { MutableList(9) { mutableStateOf("1") } }),
        onCellClick = { _, _ -> },
        selectedCell = Pair(0, 0),
        isCellNew = mutableListOf(*Array(9) { MutableList(9) { mutableStateOf(false) } })
    )
}