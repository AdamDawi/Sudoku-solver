package com.example.sudokusolver.presentation.main_screen

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.sudokusolver.common.SudokuSolution
import com.example.sudokusolver.common.TestTags
import com.example.sudokusolver.domain.use_cases.SolveSudokuUseCase
import com.example.sudokusolver.presentation.MainActivity
import com.example.sudokusolver.presentation.ui.theme.SudokuSolverTheme
import io.mockk.coEvery
import io.mockk.coVerify
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
    fun sudokuSolverText_rendering_displayedCorrectly() {
        composeRule.onNodeWithText("SUDOKU SOLVER").assertIsDisplayed()

    }

    @Test
    fun sudokuCells_rendering_displayedAllCorrectly() {
        composeRule.onAllNodesWithTag("SudokuCell").assertCountEquals(81)
    }

    @Test
    fun numberBoxes_rendering_displayedAllCorrectly() {
        composeRule.onAllNodesWithTag("NumberBox").assertCountEquals(9)
    }

    @Test
    fun solveButton_rendering_displayedCorrectly() {
        composeRule.onNodeWithText("Solve").assertIsDisplayed()
    }

    @Test
    fun clearButton_rendering_displayedCorrectly() {
        composeRule.onNodeWithContentDescription("Clear cell").assertIsDisplayed()
    }

    @Test
    fun clearAllButton_rendering_displayedCorrectly() {
        composeRule.onNodeWithContentDescription("Clear all cells").assertIsDisplayed()
    }

    @Test
    fun sudokuCellsAndBoxNumbers_click_updatesCellsCorrectly() {
        // assert all cell tags have empty test tag
        composeRule.onAllNodesWithTag(testTag = TestTags.EMPTY_CELL, useUnmergedTree = true).assertCountEquals(81)

        // click cell
        composeRule.onAllNodesWithTag("SudokuCell")[0].performClick()

        // click number box with "1"
        composeRule.onAllNodesWithTag("NumberBox")[0].performClick()

        // check if cell is updated test tag with the selected number
        composeRule.onNodeWithTag(testTag = "1", useUnmergedTree = true).assertIsDisplayed()
    }

    @Test
    fun solveButton_click_solveSudokuUseCaseCalled(){
        composeRule.onNodeWithText("Solve").performClick()

        coVerify { solveSudokuUseCase.solveSudoku(any()) }
    }

    @Test
    fun solveButton_click_allCellsAreNotEmpty(){
        composeRule.onNodeWithText("Solve").performClick()

        composeRule.onAllNodesWithTag(testTag = TestTags.EMPTY_CELL, useUnmergedTree = true).assertCountEquals(0)
    }

    @Test
    fun clearCellButton_click_cellCleared() {

        // click cell and number box with "1"
        composeRule.onAllNodesWithTag(TestTags.SUDOKU_CELL)[0].performClick()
        composeRule.onAllNodesWithTag(TestTags.NUMBER_BOX)[0].performClick()

        // check if cell is updated test tag with the selected number
        composeRule.onAllNodesWithTag(testTag = TestTags.EMPTY_CELL, useUnmergedTree = true).assertCountEquals(80)

        // click button to clear cell
        composeRule.onNodeWithContentDescription("Clear cell").performClick()

        // check if cell is cleared
        composeRule.onAllNodesWithTag(testTag = TestTags.EMPTY_CELL, useUnmergedTree = true).assertCountEquals(81)
    }

    @Test
    fun clearAllCellsButton_click_allCellsCleared() {

        // click first cell and number box with "1"
        composeRule.onAllNodesWithTag(TestTags.SUDOKU_CELL)[0].performClick()
        composeRule.onAllNodesWithTag(TestTags.NUMBER_BOX)[0].performClick()

        // click second cell and number box with "2"
        composeRule.onAllNodesWithTag(TestTags.SUDOKU_CELL)[1].performClick()
        composeRule.onAllNodesWithTag(TestTags.NUMBER_BOX)[1].performClick()

        // check if cell is updated test tag with the selected number
        composeRule.onAllNodesWithTag(testTag = TestTags.EMPTY_CELL, useUnmergedTree = true).assertCountEquals(79)

        // click button clear all cells
        composeRule.onNodeWithContentDescription("Clear all cells").performClick()

        // check if all cells are cleared
        composeRule.onAllNodesWithTag(testTag = TestTags.EMPTY_CELL, useUnmergedTree = true).assertCountEquals(81)
    }


    // region helper methods

    // endregion helper methods

    // region helper classes

    // endregion helper classes
}