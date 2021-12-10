package day10

import readInput

fun main() {
    val input = readInput("day10/Day10")

    check(partOne(input) == 464991)
    check(partTwo(input) == 3662008566)

    println(partOne(input))
    println(partTwo(input))
}

data class Brackets(
    val opening: Char,
    val closing: Char,
    val errorPoints: Int,
    val completionPoints: Int,
) {
    companion object {
        fun of(c: Char) = brackets.first { c in listOf(it.opening, it.closing) }
    }
}

val brackets = listOf(
    Brackets(opening = '(', closing = ')', errorPoints = 3, completionPoints = 1),
    Brackets(opening = '[', closing = ']', errorPoints = 57, completionPoints = 2),
    Brackets(opening = '{', closing = '}', errorPoints = 1197, completionPoints = 3),
    Brackets(opening = '<', closing = '>', errorPoints = 25137, completionPoints = 4)
)

fun partOne(input: List<String>): Int {
    return input
        .mapNotNull { illegalBracketOrNull(it) }
        .sumOf { it.errorPoints }
}

fun partTwo(input: List<String>): Long {
    val scores = input
        .filter { line -> illegalBracketOrNull(line) == null }
        .map { incompleteLine -> removeCompleteChunks(incompleteLine) }
        .map { incompleteLine ->
            incompleteLine
                .map { Brackets.of(it).closing }
                .reversed()
                .joinToString("")
        }
        .map { completionString ->
            completionString
                .map { Brackets.of(it).completionPoints }
                .fold(0L) { score, completionPoints -> (score * 5) + completionPoints }
        }
        .sorted()
    return scores[scores.size / 2]
}

fun illegalBracketOrNull(line: String): Brackets? {
    val stack = ArrayDeque<Char>()
    for (char in line) {
        if (char in brackets.map { it.opening }) {
            stack.addLast(char)
        } else {
            if (stack.last() == Brackets.of(char).opening) {
                stack.removeLast()
            } else {
                return Brackets.of(char) // found illegal bracket
            }
        }
    }
    return null
}

fun removeCompleteChunks(ln: String): String {
    val stack = ArrayDeque<Char>()
    for (c in ln) {
        if (c in brackets.map { it.opening }) {
            stack.addLast(c)
        } else {
            if (stack.last() == Brackets.of(c).opening) {
                stack.removeLast()
            }
        }
    }
    return stack.joinToString("")
}