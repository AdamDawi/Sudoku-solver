package com.example.sudokusolver.common

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList

fun SnapshotStateList<MutableList<MutableState<String>>>.toIntGrid(): Array<Array<Int>> {
    return Array(this.size) { i ->
        Array(this[i].size) { j ->
            this[i][j].value.toIntOrNull() ?: 0
        }
    }
}

fun SnapshotStateList<MutableList<MutableState<String>>>.updateFrom(result: Array<Array<Int>>) {
    for (i in 0 until 9) {
        for (j in 0 until 9) {
            this[i][j].value = result[i][j].toString()
        }
    }
}

fun SnapshotStateList<MutableList<MutableState<String>>>.clearStringState() {
    for (i in 0 until 9) {
        for (j in 0 until 9) {
            this[i][j].value = ""
        }
    }
}

fun SnapshotStateList<MutableList<MutableState<Boolean>>>.clearBooleanState() {
    for (i in 0 until 9) {
        for (j in 0 until 9) {
            this[i][j].value = false
        }
    }
}

