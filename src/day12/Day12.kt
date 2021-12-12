package day12

import readInput

fun main() {
    val input = readInput("day12/Day12")

    val paths = partOne(input)
    check(paths == 4885)
    println(paths)
}

fun partOne(input: List<String>): Int {
    val edges = input.map { line -> line.split(",") }.flatten()

    val caves = edges
        .map { it.split("-") }
        .flatten()
        .distinct()
        .map { Cave(name = it) }

    edges.forEach {
        val split = it.split("-")
        val from = caves.find { it.name == split[0] }!!
        val to = caves.find { it.name == split[1] }!!
        from.connections.add(to)
        to.connections.add(from)
    }

    val start = caves.find { it.name == "start" }!!
    val paths = mutableListOf<List<Cave>>()
    findPaths(listOf(start), paths)
    return paths.size
}

fun findPaths(currentPath: List<Cave>, paths: MutableList<List<Cave>>) {
    val currentNode = currentPath.last()

    if (currentNode.isEnd()) {
        paths.add(currentPath)
        return
    }

    val nodesToTraverse = currentNode.connections
        .filter { node -> node.isBig() || (node.isSmall() && node !in currentPath) }

    for (node in nodesToTraverse) {
        findPaths(currentPath + node, paths)
    }
}

data class Cave(
    val name: String,
    val connections: MutableList<Cave> = mutableListOf()
) {
    fun isEnd(): Boolean = name == "end"
    fun isSmall(): Boolean = name.all { it.isLowerCase() }
    fun isBig(): Boolean = name.all { it.isUpperCase() }
}

