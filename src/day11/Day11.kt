package day11

import readInput

fun main() {
    val result = solution(readInput("day11/Day11"))

    println(result.part1)
    check(result.part1 == 1649)

    println(result.part2)
    check(result.part2 == 256)
}

data class Result(var part1: Int = 0, var part2: Int = 0) {
    fun complete(): Boolean = part1 > 0 && part2 > 0
}

fun solution(input: List<String>): Result {
    val grid = input
        .map { row -> row.map { point -> point.digitToInt() }.toIntArray() }
        .toTypedArray()

    var flashes = 0
    val result = Result()
    var step = 0

    while (!result.complete().also { ++step }) {
        grid.increaseEnergyLevel()

        for (row in grid.indices)
            for (col in grid[0].indices) {
                if (grid[row][col].readyToFlash()) {
                    flashes += grid.flash(row, col)
                }
            }

        if (grid.simultaneousFlash() && result.part2 == 0) {
            result.part2 = step
        }

        if (step == 100) {
            result.part1 = flashes
        }
    }
    return result
}

fun Array<IntArray>.simultaneousFlash(): Boolean = this.map { it.map { it } }.flatten().all { it == 0 }

fun Int.readyToFlash(): Boolean = this > 9

fun Array<IntArray>.increaseEnergyLevel() {
    for (row in this.indices)
        for (col in this[0].indices) {
            this[row][col]++
        }
}

fun Array<IntArray>.flash(row: Int, col: Int): Int {
    var flashes = 1
    this[row][col] = 0

    for (r in row - 1..row + 1) {
        for (c in col - 1..col + 1) {
            if (r >= 0 && c >= 0 && r < size && c < this[0].size && !(r == row && c == col)) {
                if (this[r][c] > 0) {
                    this[r][c]++
                }
                if (this[r][c] > 9) {
                    flashes += flash(r, c)
                }
            }
        }
    }
    return flashes
}