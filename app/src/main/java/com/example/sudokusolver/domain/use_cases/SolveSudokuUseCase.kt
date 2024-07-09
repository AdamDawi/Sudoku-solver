package com.example.sudokusolver.domain.use_cases

import com.example.sudokusolver.common.SudokuSolution
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SolveSudokuUseCase(
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    suspend fun solveSudoku(sudoku: Array<Array<Int>>): SudokuSolution {
        return withContext(dispatcher) {
            if (!checkIfSudokuIsValid(sudoku)) {
                return@withContext SudokuSolution.IncorrectSudoku
            }

            return@withContext solveSudokuInternal(sudoku)
        }
    }

    private fun solveSudokuInternal(sudoku: Array<Array<Int>>): SudokuSolution {
        // find empty cell
        var row = 0
        var col = 0
        isEmptyCellInSudoku(sudoku)?.let { result ->
            row = result.first
            col = result.second
        } ?: return SudokuSolution.Success(sudoku)

        for (i in 1..9) {
            if (isSafe(sudoku, row, col, i)) {
                sudoku[row][col] = i
                val result = solveSudokuInternal(sudoku)
                if (result is SudokuSolution.Success) {
                    return result
                }
                sudoku[row][col] = UNASSIGNED
            }
        }

        return SudokuSolution.IncorrectSudoku
    }

    private fun isSafe(sudoku: Array<Array<Int>>, row: Int, col: Int, num: Int): Boolean {
        // row
        for (i in 0 until 9) {
            if (sudoku[row][i] == num) return false
        }

        // col
        for (i in 0 until 9) {
            if (sudoku[i][col] == num) return false
        }

        // box
        val startRow = row - row % 3
        val startCol = col - col % 3
        for (i in 0 until 3) {
            for (j in 0 until 3) {
                if (sudoku[startRow + i][startCol + j] == num) return false
            }
        }
        return true
    }

    private fun isEmptyCellInSudoku(sudoku: Array<Array<Int>>): Pair<Int, Int>? {
        // find empty cell
        for (i in 0 until 9) {
            for (j in 0 until 9) {
                if (sudoku[i][j] == UNASSIGNED) {
                    return Pair(i, j)
                }
            }
        }
        return null
    }

    private fun checkIfSudokuIsValid(sudoku: Array<Array<Int>>): Boolean {
        // check rows
        if (sudoku.size != 9) {
            return false
        }

        //check columns
        for (i in 0 until 9) {
            if (sudoku[i].size != 9) {
                return false
            }
        }

        // Check rows and columns for duplicates
        for (i in 0 until 9) {
            val rowSet = mutableSetOf<Int>()
            val colSet = mutableSetOf<Int>()
            for (j in 0 until 9) {
                if (sudoku[i][j] != UNASSIGNED && !rowSet.add(sudoku[i][j])) return false
                if (sudoku[j][i] != UNASSIGNED && !colSet.add(sudoku[j][i])) return false
            }
        }

        // Check 3x3 sub grids for duplicates
        for (row in 0 until 9 step 3) {
            for (col in 0 until 9 step 3) {
                val subGridSet = mutableSetOf<Int>()
                for (i in 0 until 3) {
                    for (j in 0 until 3) {
                        val value = sudoku[row + i][col + j]
                        if (value != UNASSIGNED && !subGridSet.add(value)) return false
                    }
                }
            }
        }
        return true
    }

    companion object {
        private const val UNASSIGNED = 0
    }
}