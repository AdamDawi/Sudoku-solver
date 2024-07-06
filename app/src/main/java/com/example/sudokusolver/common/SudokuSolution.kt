package com.example.sudokusolver.common

sealed class SudokuSolution{
    data class Success(val data: Array<Array<Int>>) : SudokuSolution()
    data object IncorrectSudoku : SudokuSolution()
}