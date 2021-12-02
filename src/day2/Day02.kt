package day2

import readInput

fun main() {
    var horisontal = 0
    var depth = 0
    var aim = 0
    var up = 0
    var down = 0

    readInput("day2/Day02")
        .map { it.split(" ") }
        .forEach {
            val value = it[1].toInt()
            when (it[0]) {
                "forward" -> {
                    horisontal += value
                    depth += (aim * value)
                }
                "down" -> {
                    aim += value
                    down += value
                }
                "up" -> {
                    aim -= value
                    up += value
                }
            }
        }

    println("part1 = ${horisontal * (down - up)}")
    println("part2 = ${horisontal * depth}")
}
