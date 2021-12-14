package day13

import readInput

fun main() {
    val input = readInput("day13/Day13")
    val result = partOne(input)
    println(result)
    check(result == 17)
}

data class Coordinate(val x: Int, val y: Int)

sealed class Fold(val atLine: Int) {
    class Up(atLine: Int) : Fold(atLine)
    class Left(atLine: Int) : Fold(atLine)
}

fun partOne(input: List<String>): Int {

    val coordinates = input
        .takeWhile { it.isNotBlank() }
        .map { it.split(",") }
        .map { Coordinate(it[0].toInt(), it[1].toInt()) }

    val foldInstructions = input
        .takeLastWhile { it.isNotBlank() }
        .map {
            val (instruction, line) = it.substringAfter("fold along ").split("=")
            when (instruction) {
                "x" -> Fold.Left(atLine = line.toInt())
                "y" -> Fold.Up(atLine = line.toInt())
                else -> error("Illegal instruction")
            }
        }

    var matrix = Array(coordinates.maxOf { it.y } + 1) { IntArray(coordinates.maxOf { it.x } + 1) }
    for (coordinate in coordinates) {
        matrix[coordinate.y][coordinate.x] = 1
    }

    foldInstructions.forEach { instruction ->
        matrix = when (instruction) {
            is Fold.Up -> matrix.foldUp(instruction.atLine)
            is Fold.Left -> matrix.foldLeft(instruction.atLine)
        }
    }

    return matrix.dots()
}

fun Array<IntArray>.foldUp(lines: Int): Array<IntArray> {
    val newMatrix = Array(lines) { IntArray(this[0].size) }
    for (row in 0 until lines) {
        for (col in 0 until this[0].size) {
            newMatrix[row][col] = this[row][col] + this[(lines * 2) - row][col]
        }
    }
    return newMatrix
}

fun Array<IntArray>.foldLeft(lines: Int): Array<IntArray> {
    val newMatrix = Array(this.size) { IntArray(lines) }
    for (col in 0 until lines) {
        for (row in 0 until this.size) {
            newMatrix[row][col] = this[row][col] + this[row][lines * 2 - col]
        }
    }
    return newMatrix
}

fun Array<IntArray>.print() {
    for (row in this.indices) {
        for (col in this[0].indices) {
            print(if (this[row][col] > 0) '#' else '.')
        }
        println()
    }
}

fun Array<IntArray>.dots(): Int {
    var dots = 0
    for (row in this.indices) {
        for (col in this[0].indices) {
            if (this[row][col] > 0) dots++
        }
    }
    return dots
}

