package day5

import readInput
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

fun main() {
    val input = readInput("day5/Day05")

    val linesOfVents: List<LineSegment> = input
        .map { line -> line.split(" -> ") }
        .map {
            val from = it[0].split(",")
            val to = it[1].split(",")
            LineSegment(
                from = Coordinate(from[0].toInt(), from[1].toInt()),
                to = Coordinate(to[0].toInt(), to[1].toInt())
            )
        }

    println(part1(linesOfVents))
    println(part2(linesOfVents))
}

fun part1(linesOfVents: List<LineSegment>): Int =
    linesOfVents
        .filter { line -> line.isHorizontal() || line.isVertical() }
        .flatMap { line -> line.coordinates() }
        .groupingBy { it }
        .eachCount()
        .filter { it.value > 1 }
        .count()

fun part2(linesOfVents: List<LineSegment>): Int =
    linesOfVents
        .flatMap { line -> line.coordinates() }
        .groupingBy { it }
        .eachCount()
        .filter { it.value > 1 }
        .count()

data class Coordinate(
    val x: Int,
    val y: Int,
)

data class LineSegment(
    val from: Coordinate,
    val to: Coordinate,
) {
    fun coordinates(): List<Coordinate> =
        if (isHorizontal()) {
            (min(from.x, to.x)..max(from.x, to.x)).map { Coordinate(it, from.y) }
        } else if (isVertical()) {
            (min(from.y, to.y)..max(from.y, to.y)).map { Coordinate(from.x, it) }
        } else {
            val coordinates = mutableListOf<Coordinate>()
            var x = from.x
            var y = from.y
            for (i in 0..abs(from.x - to.x)) {
                coordinates.add(Coordinate(x, y))
                x += if (from.x < to.x) 1 else -1
                y += if (from.y < to.y) 1 else -1
            }
            coordinates
        }

    fun isHorizontal(): Boolean = from.x != to.x && from.y == to.y
    fun isVertical(): Boolean = from.x == to.x && from.y != to.y
}
