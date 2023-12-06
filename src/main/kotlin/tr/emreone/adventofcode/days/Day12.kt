package tr.emreone.adventofcode.days

import tr.emreone.kotlin_utils.math.Point2D

object Day12 {

    class Area(private val width: Int, private val height: Int) {
        private val squares = Array<Array<Char>>(height) { Array(width) { ' ' } }
        lateinit var startPosition: Point2D
        lateinit var endPosition: Point2D

        fun parseSquares(rows: List<String>) {
            rows.forEachIndexed { y, row ->
                row.forEachIndexed { x, char ->
                    when(char) {
                        'S' -> {
                            this.squares[y][x] = 'a'
                            startPosition = Point2D(x.toLong(), y.toLong())
                        }
                        'E' -> {
                            this.squares[y][x] = 'z'
                            endPosition = Point2D(x.toLong(), y.toLong())
                        }
                        else -> {
                            this.squares[y][x] = char
                        }
                    }
                }
            }
        }

        private fun getSignalStrengthAt(coord: Point2D): Char {
            return this.squares[coord.y.toInt()][coord.x.toInt()]
        }

        private fun getValidNeighbors(from: Point2D): List<Point2D> {
            val neighbors = mutableListOf<Point2D>()
            val (x, y) = from.x to from.y

            if ((x - 1) in 0 until this.width) neighbors.add(Point2D(x - 1, y))
            if ((x + 1) in 0 until this.width) neighbors.add(Point2D(x + 1, y))
            if ((y - 1) in 0 until this.height) neighbors.add(Point2D(x, y - 1))
            if ((y + 1) in 0 until this.height) neighbors.add(Point2D(x, y + 1))

            val currentSignalStrength = this.getSignalStrengthAt(from)

            return neighbors
                .filter {
                    this.getSignalStrengthAt(it) <= currentSignalStrength + 1
                }
        }

        fun findSignalStrength(signalStrength: Char): List<Point2D> {
            val result = mutableListOf<Point2D>()

            this.squares.forEachIndexed { y, row ->
                row.forEachIndexed { x, char ->
                    if (char == signalStrength) {
                        result.add(Point2D(x.toLong(), y.toLong()))
                    }
                }
            }

            return result
        }

        fun bfs(start: Point2D, target: Point2D): Int {
            val queue = mutableListOf(start)
            val dist = queue
                .associateWith { 0 }
                .toMutableMap()

            while (queue.isNotEmpty()) {
                val currentSquare = queue.removeFirst()

                this.getValidNeighbors(currentSquare)
                    .filter {neighbor ->
                        !dist.contains(neighbor)
                    }
                    .forEach {neighbor ->
                        dist[neighbor] = dist[currentSquare]!! + 1
                        queue.add(neighbor)
                    }
            }
            return dist[target] ?: Int.MAX_VALUE
        }
    }

    fun part1(input: List<String>): Int {
        val area = Area(input.first().length, input.size)
        area.parseSquares(input)

        return area.bfs(area.startPosition, area.endPosition)
    }

    fun part2(input: List<String>): Int {
        val area = Area(input.first().length, input.size)
        area.parseSquares(input)

        return area.findSignalStrength('a').minOf { coord ->
            area.bfs(coord, area.endPosition)
        }
    }
}
