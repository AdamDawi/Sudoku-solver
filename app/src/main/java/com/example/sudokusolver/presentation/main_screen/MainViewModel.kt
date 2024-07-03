package com.example.sudokusolver.presentation.main_screen

import androidx.lifecycle.ViewModel
import com.example.sudokusolver.common.Result

class MainViewModel: ViewModel() {
    fun solveSudoku(sudoku: Array<Array<Int>>, row: Int, col: Int): Result {
        // check rows
        if (sudoku.size != 9) {
            return Result.IncorrectSudokuFormat
        }

        //check columns
        for (i in 0 until 9) {
            if (sudoku[i].size != 9) {
                return Result.IncorrectSudokuFormat
            }
        }
        return Result.Success(emptyArray())
    }

    fun isSafe(sudoku: Array<Array<Int>>, row: Int, col: Int, num:Int){

    }
}