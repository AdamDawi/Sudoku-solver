package com.example.sudokusolver.common

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList

/**
 * Converts the SnapshotStateList of MutableLists of MutableState<String> to a 2D array of integers.
 *
 * Each MutableState<String> is converted to an integer. If the value cannot be parsed to an integer,
 * it defaults to 0.
 *
 * @return 2D array representing the integer grid derived from SnapshotStateList<MutableList<MutableState<String>>>
 */
fun SnapshotStateList<MutableList<MutableState<String>>>.toIntGrid(): Array<Array<Int>> {
    return Array(this.size) { i ->
        Array(this[i].size) { j ->
            this[i][j].value.toIntOrNull() ?: 0
        }
    }
}

/**
 * Clears the value of each MutableState<String> in the SnapshotStateList<MutableList<MutableState<String>>>.
 * Sets each value to an empty String ("").
 */
fun SnapshotStateList<MutableList<MutableState<String>>>.clearStringState() {
    for (i in 0 until 9) {
        for (j in 0 until 9) {
            this[i][j].value = ""
        }
    }
}

/**
 * Clears the value of each MutableState<Boolean> in the SnapshotStateList<MutableList<MutableState<Boolean>>>.
 * Sets each value to false.
 */
fun SnapshotStateList<MutableList<MutableState<Boolean>>>.clearBooleanState() {
    for (i in 0 until 9) {
        for (j in 0 until 9) {
            this[i][j].value = false
        }
    }
}

