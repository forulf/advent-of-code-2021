package day4

import readInput

fun main() {
    val input = readInput("day4/Day04")
    val draws = input[0].split(",").map { it.toInt() }

    val boards = mutableListOf<Board>()
    for (i in 2..input.size step 6) {
        var board: Array<Array<Cell>> = arrayOf()
        for (j in 0..4) {
            val array = input[i + j].trim().split("\\s+".toRegex()).map { Cell(it.toInt()) }.toTypedArray()
            board += array
        }
        boards.add(Board(board))
    }

    println(part1(draws, boards))
    println(part2(draws, boards))
}

fun part1(draws: List<Int>, boards: List<Board>): Int {
    draws.forEach { number ->
        boards.forEach { board ->
            board.draw(number)
            if (board.winner) {
                return board.score()
            }
        }
    }
    throw IllegalStateException("Oops!")
}

fun part2(draws: List<Int>, boards: List<Board>): Int {
    val winningBoards = mutableListOf<Board>()

    draws.forEach { number ->
        boards.forEach { board ->
            if (!board.winner) {
                board.draw(number)
                if (board.winner) {
                    winningBoards.add(board)
                }
            }
        }
    }

    return winningBoards.last().score()
}

data class Cell(
    val number: Int,
    val drawed: Boolean = false
)

data class Board(
    val board: Array<Array<Cell>> = arrayOf(),
    var winner: Boolean = false,
    var lastDraw: Int = -1
) {
    fun score(): Int {
        val sumOfUnmarked = board.sumOf {
            it.filter { cell -> !cell.drawed }.sumOf { cell -> cell.number }
        }
        return sumOfUnmarked * lastDraw
    }

    fun draw(number: Int) {
        for (i in 0..4) {
            for (j in 0..4) {
                if (board[i][j].number == number) {
                    board[i][j] = board[i][j].copy(drawed = true)
                }
            }
        }
        if (isWin()) {
            winner = true
            lastDraw = number
        }
    }

    private fun isWin(): Boolean {
        for (i in 0..4) {
            if (board[i].all { it.drawed }) return true
        }
        for (j in 0..4) {
            var count = 0
            for (i in 0..4) {
                if (board[i][j].drawed) {
                    count++
                }
            }
            if (count == 5) return true
        }
        return false
    }
}
