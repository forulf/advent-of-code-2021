package day8

import readInput

fun main() {

    fun partOne(): Int =
        readInput("day8/Day08")
            .flatMap { it.substringAfter("|").split(" ") }
            .filter { it.length in listOf(2, 3, 4, 7) }
            .size

    fun partTwo(): Int {
        TODO()
    }

    println(partOne())
    println(partTwo())
}