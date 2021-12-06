package day6

import readText

fun main() {

    fun partOne(days: Int): Long {

        val internalTimers = LongArray(9)

        readText("day6/Day06")
            .split(",")
            .map { it.toInt() }
            .forEach { internalTimers[it]++ }

        for (day in 1..days) {
            val newborns = internalTimers[0]

            for (i in 0 until 8) {
                internalTimers[i] = internalTimers[i+1]
            }

            internalTimers[6] += newborns
            internalTimers[8] = newborns
        }

        return internalTimers.sum()
    }

    println(partOne(days = 18))
    println(partOne(days = 80))
    println(partOne(days = 256))
}
