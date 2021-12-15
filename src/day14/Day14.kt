package day14

import readInput

fun main() {
    fun part1(input: List<String>, steps: Int): Int {
        val rules = input
            .takeLastWhile { it.isNotBlank() }
            .map { it.split("->") }
            .associate { it[0].trim() to it[1].trim() }

        val template = input[0]
        var polymer = template
        repeat(steps) {
            polymer = polymer
                .windowed(2)
                .joinToString("") { it[0] + rules[it]!! } + template.last() // small hack
        }

        val mostCommon = polymer.groupBy { it }.maxOf { it.value.size }
        val leastCommon = polymer.groupBy { it }.minOf { it.value.size }
        return mostCommon - leastCommon
    }

    val input = readInput("day14/Day14")
    val result = part1(input, steps = 10)
    check(result == 3587)
    println(result)
}
