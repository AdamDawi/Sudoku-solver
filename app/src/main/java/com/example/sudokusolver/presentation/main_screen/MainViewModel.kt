package com.example.sudokusolver.presentation.main_screen

import androidx.lifecycle.ViewModel
import com.example.sudokusolver.common.Result

class MainViewModel: ViewModel() {
    private val UNASSIGNED = 0
    fun solveSudoku(sudoku: Array<Array<Int>>): Result {
        if(!checkIfSudokuIsValid(sudoku)) return Result.IncorrectSudokuFormat

        // find empty cell
        var row = 0
        var col = 0
        isEmptyCellInSudoku(sudoku)?.let { result ->
            row = result.first
            col = result.second
        } ?: return Result.Success(sudoku)

        for(i in 1..9){
            if(isSafe(sudoku, row, col, i)){
                sudoku[row][col] = i
                val result = solveSudoku(sudoku)
                if(result is Result.Success){
                    return result
                }
                sudoku[row][col] = UNASSIGNED
            }
        }

        return Result.NoResultSudoku
    }

    private fun isSafe(sudoku: Array<Array<Int>>, row: Int, col: Int, num: Int): Boolean {
        // row
        for(i in 0 until 9){
            if(sudoku[row][i] == num) return false
        }

        // col
        for(i in 0 until 9){
            if(sudoku[i][col] == num) return false
        }

        // box
        val startRow = row - row % 3
        val startCol = col - col % 3
        for(i in 0 until 3){
            for(j in 0 until 3){
                if(sudoku[startRow+i][startCol+j] == num) return false
            }
        }
        return true
    }

    private fun isEmptyCellInSudoku(sudoku: Array<Array<Int>>): Pair<Int, Int>? {
        // find empty cell
        for(i in 0 until 9){
            for(j in 0 until 9){
                if(sudoku[i][j] == UNASSIGNED){
                    return Pair(i, j)
                }
            }
        }
        return null
    }

    private fun checkIfSudokuIsValid(sudoku: Array<Array<Int>>): Boolean{
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
        return true
    }
}