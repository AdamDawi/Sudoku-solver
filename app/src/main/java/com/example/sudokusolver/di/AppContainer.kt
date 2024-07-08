package com.example.sudokusolver.di

import com.example.sudokusolver.domain.use_cases.SolveSudokuUseCase

class AppContainer {
    val solveSudokuUseCase = SolveSudokuUseCase()
}