package day3

import readInput

fun main() {
    val input = readInput("day3/Day03")
    println(part1(input))
    println(part2(input))
}

fun part1(input: List<String>): Int {
    val length = input[0].length
    val onesCount = IntArray(length)
    val zerosCount = IntArray(length)

    input.forEach {
        for (i in it.indices) {
            if (it[i] == '0') zerosCount[i]++ else onesCount[i]++
        }
    }

    val gamma = (0 until length)
        .joinToString("") { if (onesCount[it] > zerosCount[it]) "1" else "0" }

    val epsilon = (0 until length)
        .joinToString("") { if (onesCount[it] > zerosCount[it]) "0" else "1" }

    return Integer.parseInt(gamma, 2) * Integer.parseInt(epsilon, 2)
}

fun part2(inputparam: List<String>) =
    rating(inputparam, "oxygen") * rating(inputparam, "co2")

fun rating(entireList: List<String>, type: String): Int {
    var list = entireList
    for (i in 0..list[0].length) {
        val (zeroList, oneList) = list.partition { it[i] == '0' }
        if (oneList.size >= zeroList.size)
            list = if (type == "oxygen") oneList else zeroList
        else
            list = if (type == "co2") oneList else zeroList

        if (list.size == 1) break
    }
    return Integer.parseInt(list[0], 2)
}
