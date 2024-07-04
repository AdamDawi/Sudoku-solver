package com.example.sudokusolver.presentation.main_screen

import com.example.sudokusolver.common.Result
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.junit.Assert.*
import kotlin.reflect.full.declaredFunctions
import kotlin.reflect.jvm.isAccessible

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

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

    // endregion helper fields

    private lateinit var sut: MainViewModel

    @Before
    fun setup() {
        sut = MainViewModel()

    }

    @Test
    fun solveSudoku_correctSudokuPassed_shouldReturnCorrectResultSudoku() {
        // arrange

        // act
        val result = sut.solveSudoku(correctSudoku)

        // assert
        assertMatrixEquals(fakeSudokuResult, (result as Result.Success).data)
    }

    @Test
    fun solveSudoku_incorrectNumberOfRowsSudokuPassed_shouldReturnIncorrectSudokuFormatError() {
        // arrange
        // 8x9
        val sudoku = arrayOf(
            arrayOf(3, 0, 6, 5, 0, 8, 4, 0, 0),
            arrayOf(5, 2, 0, 0, 0, 0, 0, 0, 0),
            arrayOf(0, 8, 7, 0, 0, 0, 0, 3, 1),
            arrayOf(0, 0, 3, 0, 1, 0, 0, 8, 0),
            arrayOf(9, 0, 0, 8, 6, 3, 0, 0, 5),
            arrayOf(0, 5, 0, 0, 9, 0, 6, 0, 0),
            arrayOf(1, 3, 0, 0, 0, 0, 2, 5, 0),
            arrayOf(0, 0, 0, 0, 0, 0, 0, 7, 4)
        )
        // act
        val result = sut.solveSudoku(sudoku)

        // assert
        assertEquals(Result.IncorrectSudokuFormat, result)
    }

    @Test
    fun solveSudoku_incorrectNumberOfColumnsSudokuPassed_shouldReturnIncorrectSudokuFormatError() {
        // arrange
        // 9x8
        val sudoku = arrayOf(
            arrayOf(3, 0, 6, 5, 0, 8, 4, 0),
            arrayOf(5, 2, 0, 0, 0, 0, 0, 0),
            arrayOf(0, 8, 7, 0, 0, 0, 0, 3),
            arrayOf(0, 0, 3, 0, 1, 0, 0, 8),
            arrayOf(9, 0, 0, 8, 6, 3, 0, 0),
            arrayOf(0, 5, 0, 0, 9, 0, 6, 0),
            arrayOf(1, 3, 0, 0, 0, 0, 2, 5),
            arrayOf(0, 0, 0, 0, 0, 0, 0, 7),
            arrayOf(0, 0, 5, 2, 0, 6, 3, 0)
        )
        // act
        val result = sut.solveSudoku(sudoku)

        // assert
        assertEquals(Result.IncorrectSudokuFormat, result)
    }

    @Test
    fun solveSudoku_sudokuWithNoSolutionPassed_shouldReturnNoSolutionError() {
        // arrange
        val sudoku = arrayOf(
            arrayOf(3, 0, 6, 5, 0, 8, 4, 0, 0),
            arrayOf(5, 2, 0, 0, 0, 0, 0, 0, 0),
            arrayOf(0, 8, 7, 0, 0, 0, 0, 3, 1),
            arrayOf(0, 0, 3, 0, 1, 0, 0, 8, 0),
            arrayOf(9, 0, 0, 8, 6, 3, 0, 0, 5),
            arrayOf(0, 5, 0, 0, 9, 0, 6, 0, 0),
            arrayOf(1, 3, 0, 0, 0, 0, 2, 5, 0),
            arrayOf(0, 0, 0, 0, 0, 0, 0, 7, 4),
            arrayOf(0, 0, 5, 2, 0, 6, 3, 0, 3)
        )
        // act
        val result = sut.solveSudoku(sudoku)

        // assert
        assertEquals(Result.NoResultSudoku, result)
    }

    @Test
    fun isSafe_notDuplicatedNumPassed_shouldReturnTrue() {
        // arrange
        val isSafeMethod = sut::class.declaredFunctions.find { it.name == "isSafe" }
        isSafeMethod?.isAccessible = true

        // act
        val result = isSafeMethod?.call(sut, correctSudoku, 0, 1, 1) as Boolean

        // assert
        assertEquals(true, result)
    }

    @Test
    fun isSafe_duplicatedNumWithinBoxPassed_shouldReturnFalse() {
        // arrange
        val isSafeMethod = sut::class.declaredFunctions.find { it.name == "isSafe" }
        isSafeMethod?.isAccessible = true

        // act
        val result = isSafeMethod?.call(sut, correctSudoku, 0, 0, 3) as Boolean

        // assert
        assertEquals(false, result)
    }

    @Test
    fun isSafe_duplicatedNumWithinRowPassed_shouldReturnFalse() {
        // arrange
        val isSafeMethod = sut::class.declaredFunctions.find { it.name == "isSafe" }
        isSafeMethod?.isAccessible = true

        // act
        val result = isSafeMethod?.call(sut, correctSudoku, 0, 0, 1) as Boolean

        // assert
        assertEquals(false, result)
    }

    @Test
    fun isSafe_duplicatedNumWithinColPassed_shouldReturnFalse() {
        // arrange
        val isSafeMethod = sut::class.declaredFunctions.find { it.name == "isSafe" }
        isSafeMethod?.isAccessible = true

        // act
        val result = isSafeMethod?.call(sut, correctSudoku, 3, 1, 2) as Boolean

        // assert
        assertEquals(false, result)
    }


    // region helper methods

    private fun assertMatrixEquals(expected: Array<Array<Int>>, actual: Array<Array<Int>>) {
        if (expected.size != actual.size) {
            throw AssertionError("Both arrays must be the same size: expected=${expected.size}, actual=${actual.size}")
        }

        for (i in 0 until 9) {
            if (expected[i].size != actual[i].size) {
                throw AssertionError("Both arrays must be the same size: expected=${expected[i].size}, actual=${actual[i].size}")
            }
        }

        // Compare all elements
        for (i in 0 until 9) {
            for (j in 0 until 9) {
                if (expected[i][j] != actual[i][j]) {
                    throw AssertionError("Various elements in row $i and column $j: expected=${expected[i][j]}, actual=${actual[i][j]}")
                }
            }
        }
    }

    // endregion helper methods

    // region helper classes

    // endregion helper classes
}