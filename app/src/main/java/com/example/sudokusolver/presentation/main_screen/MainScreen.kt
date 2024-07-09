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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sudokusolver.common.SudokuSolution
import com.example.sudokusolver.common.TestTags
import com.example.sudokusolver.common.clearBooleanState
import com.example.sudokusolver.common.clearStringState
import com.example.sudokusolver.common.toIntGrid
import com.example.sudokusolver.presentation.main_screen.components.NumberBox
import com.example.sudokusolver.presentation.main_screen.components.SudokuBoard
import com.example.sudokusolver.presentation.ui.theme.FadedOrange

@Composable
fun MainScreen(
    viewModel: MainViewModel
) {
    val context = LocalContext.current
    // Initialize the grid with empty cells with spread operator (*)
    val sudokuGrid = remember { mutableStateListOf(*Array(9) { MutableList(9) { mutableStateOf("") } }) }
    val isCellModified = remember { mutableStateListOf(*Array(9) { MutableList(9) { mutableStateOf(false) } }) }
    var selectedCell by remember { mutableStateOf(Pair(0, 0)) }
    val isEnableSolveButton by viewModel.isEnableSolveButton.collectAsState()
    val solveResult by viewModel.solveResult.collectAsState()

    LaunchedEffect(solveResult) {
        when (solveResult) {
            is SudokuSolution.IncorrectSudoku -> {
                Toast.makeText(context, "Incorrect sudoku", Toast.LENGTH_SHORT).show()
            }
            is SudokuSolution.Success -> {
                (solveResult as SudokuSolution.Success).let { result ->
                    for (i in 0 until 9) {
                        for (j in 0 until 9) {
                            if (sudokuGrid[i][j].value != result.data[i][j].toString()) {
                                sudokuGrid[i][j].value = result.data[i][j].toString()
                                isCellModified[i][j].value = true
                            }
                        }
                    }
                }
            }
            null -> {
                // initialize state
            }
        }
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
            grid = sudokuGrid,
            onCellClick = { i, j -> selectedCell = i to j },
            selectedCell = selectedCell,
            isCellNew = isCellModified
        )

        Spacer(modifier = Modifier.height(20.dp))

        NumbersRow(
            modifier = Modifier.fillMaxWidth()
        ) { number ->
            sudokuGrid[selectedCell.first][selectedCell.second].value = number.toString()
        }

        ButtonsRow(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            onSolveClick = {
                viewModel.solveSudoku(sudokuGrid.toIntGrid())
            },
            onClearClick = {
                if (sudokuGrid[selectedCell.first][selectedCell.second].value.isNotEmpty()) {
                    sudokuGrid[selectedCell.first][selectedCell.second].value = ""
                }
            },
            onClearAllClick = {
                sudokuGrid.clearStringState()
                isCellModified.clearBooleanState()
            },
            isEnableSolveButton = isEnableSolveButton
        )
    }
}

@Composable
private fun Title(
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
private fun NumbersRow(
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
                    modifier = Modifier.testTag(TestTags.NUMBER_BOX),
                    number = i,
                    onClick = { onNumberClick(i)}
                )
            }
        }
    }
}

@Composable
private fun ButtonsRow(
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
                contentDescription = "Clear cell",
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
                fontSize = 18.sp,
                color = Color.White
            )
        }

        IconButton(onClick = {
            onClearAllClick()
        }) {
            Icon(
                modifier = Modifier.size(35.dp),
                imageVector = Icons.Default.Delete,
                contentDescription = "Clear all cells",
                tint = FadedOrange
            )
        }
    }
}