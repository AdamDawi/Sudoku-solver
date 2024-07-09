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

    private val _isEnableSolveButton = MutableStateFlow(true)
    val isEnableSolveButton: StateFlow<Boolean> = _isEnableSolveButton
    fun solveSudoku(sudoku: Array<Array<Int>>){
        // for launch effect to work properly value = null is required
        _solveResult.value = null

        viewModelScope.launch {
            _isEnableSolveButton.value = false
            _solveResult.value = solveSudokuUseCase.solveSudoku(sudoku)
            _isEnableSolveButton.value = true
        }
    }
}