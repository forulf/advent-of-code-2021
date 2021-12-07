package day7

import readText
import kotlin.math.abs

fun main() {

    fun partOne(positions: List<Int>): Int {
        val arr = IntArray(positions.maxOrNull()!!)
        for (pos in arr.indices) {
            for (number in positions) {
                arr[pos] += abs(number - pos)
            }
        }
        return arr.minOrNull()!!
    }

    fun partTwo(positions: List<Int>): Int {
        val arr = IntArray(positions.maxOrNull()!!)
        for (pos in arr.indices) {
            for (number in positions) {
                arr[pos] += (0 until abs(number - pos) + 1).sum()
            }
        }
        return arr.minOrNull()!!
    }

    val numbers = readText("day7/Day07")
        .split(",")
        .map { it.toInt() }

    println(partOne(numbers))
    println(partTwo(numbers))
}

