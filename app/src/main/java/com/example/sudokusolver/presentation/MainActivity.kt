package com.example.sudokusolver.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.sudokusolver.presentation.main_screen.MainScreen
import com.example.sudokusolver.presentation.main_screen.MainViewModel
import com.example.sudokusolver.presentation.ui.theme.SudokuSolverTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SudokuSolverTheme {
                val viewModel: MainViewModel by viewModels()
                MainScreen(viewModel = viewModel)
            }
        }
    }
}