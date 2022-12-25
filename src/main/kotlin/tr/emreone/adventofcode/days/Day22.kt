package tr.emreone.adventofcode.days

import tr.emreone.utils.Logger.logger
import tr.emreone.utils.math.Point2D
import java.lang.StringBuilder

object Day22 {

    private val COMMAND_GROUP_PATTERN = """(\d+)(L|R)""".toRegex()

    const val MAP_WALL = '#';
    const val MAP_CLEAN = '.';
    const val MAP_EMPTY = ' ';

    enum class Direction(val value: Int) {
        RIGHT(0),
        DOWN(1),
        LEFT(2),
        UP(3);

        fun turn(char: Char): Direction {
            return when (char) {
                'L' -> values()[(value + 3) % 4]
                'R' -> values()[(value + 1) % 4]
                else -> throw IllegalArgumentException("Invalid direction: $char")
            }
        }
    }

    enum class CubeSide(val value: Int) {
        ONE(1),
        TWO(2),
        THREE(3),
        FOUR(4),
        FIVE(5),
        SIX(6);
    }

    class Board(val map: Array<Array<Char>>) {
        val height = map.size.toLong()
        val width = map.first().size.toLong()
        var cubeSize: Int = 0

        var position = Point2D(0, 0)
        var direction = Direction.RIGHT
        var cubeSide: CubeSide = CubeSide.ONE

        companion object {
            fun parseMap(input: List<String>): Board {
                val width = input.maxOf { it.length }
                val height = input.size

                val board = Board(Array(height) { Array(width) { MAP_EMPTY } })

                input.forEachIndexed { y, line ->
                    line.forEachIndexed { x, char ->
                        board.map[y][x] = char
                    }
                }

                return board
            }
        }

        fun goToStartPosition() {
            val initialX = this.map.first().indexOfFirst { it == MAP_CLEAN }
            this.position = Point2D(initialX.toLong(), 0)

            this.direction = Direction.RIGHT
        }

        private fun charAt(position: Point2D): Char {
            return this.map[position.y.toInt()][position.x.toInt()]
        }


        private fun nextPosition(from: Point2D): Point2D {
            return when (this.direction) {
                Direction.RIGHT -> Point2D((from.x + 1).mod(this.width), from.y)
                Direction.DOWN -> Point2D(from.x, (from.y + 1).mod(this.height))
                Direction.LEFT -> Point2D((from.x - 1).mod(this.width), from.y)
                Direction.UP -> Point2D(from.x, (from.y - 1).mod(this.height))
            }
        }

        fun move(steps: Int) {
            // walk until wall,
            for (i in 1..steps) {
                var nPos = nextPosition(this.position)

                while (this.charAt(nPos) == MAP_EMPTY) {
                    nPos = nextPosition(nPos)
                }
                if (this.charAt(nPos) == MAP_WALL) {
                    break
                }

                this.position = nPos
            }
        }

        private fun nextPositionOnCube(from: Point2D, direction: Direction): Pair<Point2D, Direction> {
            /*return when (direction) {
                Direction.RIGHT -> Point2D((from.x + 1).mod(this.width), from.y)
                Direction.DOWN -> Point2D(from.x, (from.y + 1).mod(this.height))
                Direction.LEFT -> Point2D((from.x - 1).mod(this.width), from.y)
                Direction.UP -> Point2D(from.x, (from.y - 1).mod(this.height))
            }
            */
            return Point2D(0,0) to Direction.RIGHT
        }

        fun moveOnCube(steps: Int) {
            for (i in 1..steps) {
                var (nPos, nDir) = nextPositionOnCube(this.position, this.direction)

                if (this.charAt(nPos) == MAP_WALL) {
                    break
                }

                this.position = nPos
                this.direction = nDir
            }
        }

        fun turn(char: Char) {
            direction = direction.turn(char)
        }

        fun calculatePassword(): Long {
            if (this.cubeSize == 0) {
                return 1_000 * (position.y + 1) + 4 * (position.x + 1) + direction.value
            }
            else {
                return 1_000 * (position.y + 1) + 4 * (position.x + 1) + cubeSide.value
            }
        }

        fun printMap(): String {
            val sb = StringBuilder()
            sb.appendLine()

            this.map.forEachIndexed { y, line ->
                line.forEachIndexed { x, char ->
                    if (x == position.x.toInt() && y == position.y.toInt()) {
                        when (direction) {
                            Direction.RIGHT -> sb.append('>')
                            Direction.DOWN -> sb.append('v')
                            Direction.LEFT -> sb.append('<')
                            Direction.UP -> sb.append('^')
                        }
                    } else {
                        sb.append(char)
                    }
                }
                sb.appendLine()
            }

            return sb.toString()
        }
    }


    fun part1(input: String): Long {
        val (inputBoard, walkCommands) = input.split("\n\n")

        val board = Board.parseMap(inputBoard.lines())
        board.goToStartPosition()


        COMMAND_GROUP_PATTERN.findAll(walkCommands)
            .map { match ->
                val steps = match.groupValues[1].toInt()
                val direction = match.groupValues[2].first()

                steps to direction
            }
            .toList()
            .forEach { (steps, direction) ->
                board.move(steps)
                board.turn(direction)
            }

        """(\d+)$""".toRegex().find(walkCommands)?.destructured?.let { (steps) ->
            board.move(steps.toInt())
        }

        return board.calculatePassword()
    }

    fun part2(input: String, cubeSize: Int = 50): Long {
        val (inputBoard, walkCommands) = input.split("\n\n")

        val board = Board.parseMap(inputBoard.lines())
        board.cubeSize = cubeSize
        board.goToStartPosition()

        COMMAND_GROUP_PATTERN.findAll(walkCommands)
            .map { match ->
                val steps = match.groupValues[1].toInt()
                val direction = match.groupValues[2].first()

                steps to direction
            }
            .toList()
            .forEach { (steps, direction) ->
                board.moveOnCube(steps)
                board.turn(direction)
            }

        """(\d+)$""".toRegex().find(walkCommands)?.destructured?.let { (steps) ->
            board.move(steps.toInt())
        }

        return board.calculatePassword()
    }
}
