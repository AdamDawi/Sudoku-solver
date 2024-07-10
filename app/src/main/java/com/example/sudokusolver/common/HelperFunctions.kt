package com.example.sudokusolver.common

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.toMutableStateList

/**
 * Converts the MutableList of MutableLists of MutableState<String> to a 2D array of integers.
 *
 * Each MutableState<String> is converted to an integer. If the value cannot be parsed to an integer,
 * it defaults to 0.
 *
 * @return 2D array representing the integer grid derived from MutableList<MutableList<MutableState<String>>>
 */
fun MutableList<MutableList<MutableState<String>>>.toIntGrid(): Array<Array<Int>> {
    return Array(this.size) { i ->
        Array(this[i].size) { j ->
            this[i][j].value.toIntOrNull() ?: 0
        }
    }
}

/**
 * Clears the value of each MutableState<String> in the MutableList<MutableList<MutableState<String>>>.
 * Sets each value to an empty String ("").
 */
fun MutableList<MutableList<MutableState<String>>>.clearStringState() {
    for (i in 0 until 9) {
        for (j in 0 until 9) {
            this[i][j].value = ""
        }
    }
}

/**
 * Clears the value of each MutableState<Boolean> in the MutableList<MutableList<MutableState<Boolean>>>.
 * Sets each value to false.
 */
fun MutableList<MutableList<MutableState<Boolean>>>.clearBooleanState() {
    for (i in 0 until 9) {
        for (j in 0 until 9) {
            this[i][j].value = false
        }
    }
}

/**
 * Creates a Saver for a 2D grid of mutable states.
 *
 * This function creates a Saver that can serialize and deserialize a 2D grid of mutable states.
 * The grid is represented as a mutable list of mutable lists, where each cell contains a mutable state.
 *
 * @return A Saver instance that can handle serialization and deserialization of the grid.
 *
 * @param T The type of value stored in each cell's mutable state.
 */
fun <T> createGridSaver(): Saver<MutableList<MutableList<MutableState<T>>>, Any> {
    return listSaver(
        save = { grid ->
            grid.map { row -> row.map { cell -> cell.value } }
        },
        restore = { saved ->
            saved.map { row ->
                row.map { cell ->
                    mutableStateOf(cell)
                }.toMutableList()
            }.toMutableStateList()
        }
    )
}
