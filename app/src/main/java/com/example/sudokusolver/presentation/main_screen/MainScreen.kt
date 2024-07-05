package com.example.sudokusolver.presentation.main_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sudokusolver.common.Result
import com.example.sudokusolver.common.clearState
import com.example.sudokusolver.common.toIntGrid
import com.example.sudokusolver.common.updateFrom
import com.example.sudokusolver.presentation.main_screen.components.NumberBox
import com.example.sudokusolver.presentation.main_screen.components.SudokuBoard

@Composable
fun MainScreen(
    viewModel: MainViewModel
) {
    val grid = remember { mutableStateListOf(*Array(9) { MutableList(9) { mutableStateOf("") } }) }
    var selectedCell by remember { mutableStateOf(Pair(0, 0)) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier
                .padding(32.dp),
            text = "SUDOKU SOLVER",
            fontWeight = FontWeight.SemiBold,
            fontSize = 32.sp
        )
        Spacer(modifier = Modifier.height(16.dp))

        SudokuBoard(
            grid = grid,
            onCellClick = { i, j -> selectedCell = i to j },
            selectedCell = selectedCell
        )
        Spacer(modifier = Modifier.height(15.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            for(i in 1..9){
                key(i) {
                    NumberBox(
                        modifier = Modifier.clickable { grid[selectedCell.first][selectedCell.second].value = i.toString() },
                        number = i)
                }
            }
        }

        Spacer(modifier = Modifier.height(15.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = {
                val result = (viewModel.solveSudoku(grid.toIntGrid()) as Result.Success).data
                grid.updateFrom(result)
            }) {
                Text(
                    text = "Solve",
                    fontSize = 18.sp
                )
            }
            Button(onClick = {
                grid[selectedCell.first][selectedCell.second].value = ""
            }) {
                Text(
                    text = "Clear",
                    fontSize = 18.sp
                )
            }

            Button(onClick = {
                grid.clearState()
            }) {
                Text(
                    text = "Clear all",
                    fontSize = 18.sp
                )
            }
        }
    }
}




