package day9

import readInput

fun main() {
    val heightmap = readInput("day9/Day09")
        .map { row -> row.map { point -> point.digitToInt() }.toIntArray() }
        .toTypedArray()

    println(partOne(heightmap))
    println(partTwo(heightmap))
}

fun partOne(heightmap: Array<IntArray>): Int {
    var lowPoints = 0
    for (row in heightmap.indices) {
        for (col in 0 until heightmap[0].size) {
            val point = Point(row, col, heightmap[row][col])
            if (heightmap.isLowPoint(point)) {
                lowPoints += point.value + 1
            }
        }
    }
    return lowPoints
}

fun partTwo(heightmap: Array<IntArray>): Int {
    val basins = mutableListOf<Set<Point>>()
    for (row in heightmap.indices) {
        for (col in 0 until heightmap[0].size) {
            val point = Point(row, col, heightmap[row][col])
            if (heightmap.isLowPoint(point)) {
                val basin = mutableSetOf(point)
                heightmap.findBasin(point, basin)
                basins += basin
            }
        }
    }
    return basins
        .map { it.size }
        .sortedDescending()
        .take(3)
        .reduce { acc, value -> acc * value }
}

data class Point(val row: Int, val col: Int, val value: Int)

fun Array<IntArray>.findBasin(point: Point, points: MutableSet<Point>) {
    val adjacents = listOfNotNull(left(point), right(point), up(point), down(point)
    ).filter { it.value in (point.value + 1)..8 }

    points += adjacents
    for (adjacent in adjacents) {
        findBasin(adjacent, points)
    }
}

fun Array<IntArray>.isLowPoint(point: Point): Boolean =
    point.value < listOfNotNull(left(point), right(point), up(point), down(point)).minOf { it.value }

fun Array<IntArray>.left(point: Point): Point? =
    if (point.col > 0) Point(point.row, point.col - 1, this[point.row][point.col - 1]) else null

fun Array<IntArray>.right(point: Point): Point? =
    if (point.col < this[0].size - 1) Point(point.row, point.col + 1, this[point.row][point.col + 1]) else null

fun Array<IntArray>.up(point: Point): Point? =
    if (point.row > 0) Point(point.row - 1, point.col, this[point.row - 1][point.col]) else null

fun Array<IntArray>.down(point: Point): Point? =
    if (point.row < this.size - 1) Point(point.row + 1, point.col, this[point.row + 1][point.col]) else null
