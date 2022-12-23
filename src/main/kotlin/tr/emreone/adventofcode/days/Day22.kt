package tr.emreone.adventofcode.days

import tr.emreone.utils.math.Coords
import tr.emreone.utils.math.Point2D

object Day22 {

    enum class Direction(val value: Int) {
        RIGHT (0),
        DOWN (1),
        LEFT (2),
        UP (3);

        fun turn(char: Char): Direction {
            return when (char) {
                'L' -> values()[(value + 3) % 4]
                'R' -> values()[(value + 1) % 4]
                else -> throw IllegalArgumentException("Invalid direction: $char")
            }
        }
    }

    class Board(val map: Array<Array<Char>>) {
        var position = Point2D(0, 0)
        var currentDirection = Direction.RIGHT

        companion object {
            fun parseMap(input: List<String>) {
                val width = input.first().length
                val height = input.size

                val board = Board(Array(width) { Array(height) { ' ' } })

                input.forEachIndexed { y, line ->
                    line.forEachIndexed { x, char ->
                        board.map[x][y] = char
                    }
                }

                val initialX = input.first().indexOfFirst { it != ' ' }
                board.position = Point2D(initialX.toLong(), 0)
            }
        }

        fun move(steps: Int) {
            // walk until wall,
            // if end of map is reached, beam to the opposite side of the map if possible
        }

        fun turn(char: Char) {
            currentDirection = currentDirection.turn(char)
        }

        fun calculatePassword(x: Int, y: Int, direction: Direction): Int {
            return 1_000 * y + 4 * x + direction.value
        }
    }


    fun part1(input: String): Int {
        val (inputBoard, walkCommands) = input.split("\n\n")

        val board = Board.parseMap(inputBoard.lines())

        var index = 0
        while(index in walkCommands.indices) {
            // TODO: next command -> either a number or L,R

        }

        return 0
    }

    fun part2(input: String): Int {

        return 0
    }
}
