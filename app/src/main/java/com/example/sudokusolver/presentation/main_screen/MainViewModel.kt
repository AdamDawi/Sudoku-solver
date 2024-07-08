package com.example.sudokusolver.presentation.main_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sudokusolver.common.SudokuSolution
import com.example.sudokusolver.domain.use_cases.SolveSudokuUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val solveSudokuUseCase: SolveSudokuUseCase
): ViewModel() {

    private val _solveResult = MutableStateFlow<SudokuSolution?>(null)
    val solveResult: StateFlow<SudokuSolution?> = _solveResult
    fun solveSudoku(sudoku: Array<Array<Int>>){
        viewModelScope.launch {
            val result = solveSudokuUseCase.solveSudoku(sudoku)
            _solveResult.value = result
        }
    }
}