package day1

import readInputAsInt

fun main() {
    fun part1(input: List<Int>): Int {
        return input
            .zipWithNext()
            .count { it.second > it.first }
    }

    fun part2(input: List<Int>): Int {
        return input
            .windowed(3)
            .zipWithNext()
            .count { it.second.sum() > it.first.sum() }
    }

    val testInput = readInputAsInt("day1/Day01_test")
    check(part1(testInput) == 7)

    val input = readInputAsInt("day1/Day01")
    println(part1(input))
    println(part2(input))
}
