package com.example.sudokusolver.presentation.main_screen

import androidx.activity.compose.setContent
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.sudokusolver.common.SudokuSolution
import com.example.sudokusolver.domain.use_cases.SolveSudokuUseCase
import com.example.sudokusolver.presentation.MainActivity
import com.example.sudokusolver.presentation.ui.theme.SudokuSolverTheme
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock

@RunWith(AndroidJUnit4::class)
class MainScreenKtTest {

    // region constants
    private val fakeSudokuResult = arrayOf(
        arrayOf(3, 1, 6, 5, 7, 8, 4, 9, 2),
        arrayOf(5, 2, 9, 1, 3, 4, 7, 6, 8),
        arrayOf(4, 8, 7, 6, 2, 9, 5, 3, 1),
        arrayOf(2, 6, 3, 4, 1, 5, 9, 8, 7),
        arrayOf(9, 7, 4, 8, 6, 3, 1, 2, 5),
        arrayOf(8, 5, 1, 7, 9, 2, 6, 4, 3),
        arrayOf(1, 3, 8, 9, 4, 7, 2, 5, 6),
        arrayOf(6, 9, 2, 3, 5, 1, 8, 7, 4),
        arrayOf(7, 4, 5, 2, 8, 6, 3, 1, 9)
    )

    private val correctSudoku = arrayOf(
        arrayOf(3, 0, 6, 5, 0, 8, 4, 0, 0),
        arrayOf(5, 2, 0, 0, 0, 0, 0, 0, 0),
        arrayOf(0, 8, 7, 0, 0, 0, 0, 3, 1),
        arrayOf(0, 0, 3, 0, 1, 0, 0, 8, 0),
        arrayOf(9, 0, 0, 8, 6, 3, 0, 0, 5),
        arrayOf(0, 5, 0, 0, 9, 0, 6, 0, 0),
        arrayOf(1, 3, 0, 0, 0, 0, 2, 5, 0),
        arrayOf(0, 0, 0, 0, 0, 0, 0, 7, 4),
        arrayOf(0, 0, 5, 2, 0, 6, 3, 0, 0)
    )
    // endregion constants

    // region helper fields

    @Mock
    lateinit var solveSudokuUseCase: SolveSudokuUseCase

    private lateinit var viewModel: MainViewModel

    // endregion helper fields

    //launch a new component activity
    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() = runTest{
        // Mock the SolveSudokuUseCase
        solveSudokuUseCase = mockk()

        // Mock the suspend function
        coEvery { solveSudokuUseCase.solveSudoku(any()) } returns SudokuSolution.Success(fakeSudokuResult)


        // Initialize the ViewModel with the mocked UseCase
        viewModel = MainViewModel(solveSudokuUseCase)

        composeRule.activity.setContent {
            SudokuSolverTheme {
                MainScreen(viewModel = viewModel)
            }
        }
    }

    @Test
    fun clickSolveButton_correctSudoku_displaySudoku() {
        composeRule.onNodeWithText("Solve").performClick()
        composeRule.onNodeWithText("1").assertExists()
    }

    // region helper methods

    // endregion helper methods

    // region helper classes

    // endregion helper classes
}