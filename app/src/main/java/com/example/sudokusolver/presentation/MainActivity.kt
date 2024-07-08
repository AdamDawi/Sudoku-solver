package com.example.sudokusolver.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.sudokusolver.di.MyApplication
import com.example.sudokusolver.presentation.main_screen.MainScreen
import com.example.sudokusolver.presentation.main_screen.MainViewModel
import com.example.sudokusolver.presentation.ui.theme.SudokuSolverTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SudokuSolverTheme {
                val appContainer = (application as MyApplication).appContainer
                MainScreen(viewModel = MainViewModel(appContainer.solveSudokuUseCase))
            }
        }
    }
}