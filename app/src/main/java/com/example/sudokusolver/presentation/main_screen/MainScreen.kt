package com.example.sudokusolver.presentation.main_screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sudokusolver.common.Result
import com.example.sudokusolver.common.clearBooleanState
import com.example.sudokusolver.common.clearStringState
import com.example.sudokusolver.common.toIntGrid
import com.example.sudokusolver.common.updateFrom
import com.example.sudokusolver.presentation.main_screen.components.NumberBox
import com.example.sudokusolver.presentation.main_screen.components.SudokuBoard
import com.example.sudokusolver.presentation.ui.theme.FadedOrange
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    viewModel: MainViewModel
) {
    val context = LocalContext.current
    // Initialize the grid with empty cells with spread operator (*)
    val grid = remember { mutableStateListOf(*Array(9) { MutableList(9) { mutableStateOf("") } }) }
    val isCellNew = remember { mutableStateListOf(*Array(9) { MutableList(9) { mutableStateOf(false) } }) }
    var selectedCell by remember { mutableStateOf(Pair(0, 0)) }
    var isEnableSolveButton by remember {
        mutableStateOf(true)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Title(modifier = Modifier.padding(32.dp))

        SudokuBoard(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .background(Color.White),
            grid = grid,
            onCellClick = { i, j -> selectedCell = i to j },
            selectedCell = selectedCell,
            isCellNew = isCellNew
        )

        Spacer(modifier = Modifier.height(20.dp))

        NumbersRow(
            modifier = Modifier.fillMaxWidth()
        ) { number ->
            grid[selectedCell.first][selectedCell.second].value = number.toString()
        }

        ButtonsRow(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            onSolveClick = {
                isEnableSolveButton = false
                viewModel.solveSudoku(grid.toIntGrid()) { result ->
                    when (result) {
                        Result.IncorrectSudoku ->{
                            CoroutineScope(Dispatchers.Main).launch {
                                Toast.makeText(context, "Incorrect sudoku", Toast.LENGTH_SHORT).show()
                            }
                            isEnableSolveButton = true
                        }
                        is Result.Success -> {
                            for (i in 0 until 9) {
                                for (j in 0 until 9) {
                                    if (grid[i][j].value != result.data[i][j].toString()) {
                                        grid[i][j].value = result.data[i][j].toString()
                                        isCellNew[i][j].value = true
                                    }
                                }
                            }
                            grid.updateFrom(result.data)
                            isEnableSolveButton = true
                        }
                    }
                }
            },
            onClearClick = {
                grid[selectedCell.first][selectedCell.second].value = ""
            },
            onClearAllClick = {
                grid.clearStringState()
                isCellNew.clearBooleanState()
            },
            isEnableSolveButton = isEnableSolveButton
        )
    }
}

@Composable
fun Title(
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        text = "SUDOKU SOLVER",
        fontWeight = FontWeight.SemiBold,
        fontSize = 32.sp
    )
}

@Composable
fun NumbersRow(
    modifier: Modifier = Modifier,
    onNumberClick: (Int) -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        for(i in 1..9){
            key(i) {
                NumberBox(
                    number = i,
                    onClick = { onNumberClick(i)}
                )
            }
        }
    }
}

@Composable
fun ButtonsRow(
    modifier: Modifier = Modifier,
    onSolveClick: () -> Unit,
    onClearClick: () -> Unit,
    onClearAllClick: () -> Unit,
    isEnableSolveButton: Boolean
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Bottom
    ) {
        IconButton(onClick = {
            onClearClick()
        }) {
            Icon(
                modifier = Modifier.size(35.dp),
                imageVector = Icons.Default.Clear,
                contentDescription = "Clear",
                tint = FadedOrange
            )
        }

        Button(onClick = {
            onSolveClick()
        },
            colors = ButtonDefaults.buttonColors(containerColor = FadedOrange, disabledContainerColor = Color.Gray),
            enabled = isEnableSolveButton
        ) {
            Text(
                text = "Solve",
                fontSize = 18.sp
            )
        }

        IconButton(onClick = {
            onClearAllClick()
        }) {
            Icon(
                modifier = Modifier.size(35.dp),
                imageVector = Icons.Default.Delete,
                contentDescription = "Clear all",
                tint = FadedOrange
            )
        }
    }
}




