package com.example.sudokusolver.common

sealed class Result{
    data class Success(val data: Array<Array<Int>>) : Result()
    data object IncorrectSudokuFormat : Result()
    data object NoResultSudoku : Result()
}