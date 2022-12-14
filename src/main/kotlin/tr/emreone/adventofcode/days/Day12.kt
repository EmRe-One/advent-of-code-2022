package tr.emreone.adventofcode.days

import tr.emreone.utils.math.Coords

object Day12 {

    data class Square(
        var coord: Coords = Coords(-1, -1),
        var signalStrength: Char = '·'
    )

    class Area(private val width: Int, private val height: Int) {
        private val squares = Array<Array<Square>>(height) { Array(width) { Square() } }
        var startPosition = Square()
        var endPosition = Square()
        private val cache: MutableMap<Square, List<Square>?> = mutableMapOf()

        fun parseSquares(rows: List<String>) {
            rows.forEachIndexed { y, row ->
                row.forEachIndexed { x, char ->
                    this.squares[y][x].coord = Coords(x, y)
                    if (char == 'S') {
                        this.squares[y][x].signalStrength = 'a'
                        this.startPosition = this.squares[y][x]
                    }
                    else {
                        this.squares[y][x].signalStrength = char
                        if (char == 'E') {
                            this.endPosition = this.squares[y][x]
                        }
                    }
                }
            }
        }

        private fun getSignalStrengthAt(square: Square): Char {
            return this.squares[square.coord.second][square.coord.first].signalStrength
        }

        private fun getValidNeighbors(from: Square, ignoredSquares: List<Square>): List<Square> {
            val neighbors = mutableListOf<Square>()
            val (x, y) = from.coord

            if ((x - 1) in 0 until this.width) neighbors.add(this.squares[y][x - 1])
            if ((x + 1) in 0 until this.width) neighbors.add(this.squares[y][x + 1])
            if ((y - 1) in 0 until this.height) neighbors.add(this.squares[y - 1][x])
            if ((y + 1) in 0 until this.height) neighbors.add(this.squares[y + 1][x])

            val currentSignalStrength = this.getSignalStrengthAt(from)

            return neighbors
                .filter {
                    val neighborStrength = this.getSignalStrengthAt(it)

                    if (currentSignalStrength in arrayOf('y', 'z')) {
                        neighborStrength in arrayOf('y', 'z', 'E')
                    }
                    else {
                        neighborStrength in arrayOf(currentSignalStrength, currentSignalStrength + 1)
                    }
                }
                .filter { !ignoredSquares.contains(it) }
        }

        fun getShortestPathToE(
            from: Square,
            comingFrom: List<Square> = listOf(),
        ): List<Square>? {

            if (getSignalStrengthAt(from) == 'E') {
                return listOf(from)
            }
            if (this.cache.containsKey(from)) {
                return this.cache[from]
            }

            // get neighbors with signalStrength plus 0 or 1
            val neighbors = getValidNeighbors(from, comingFrom)

            val listOfPossibleNeighbors = neighbors
                .mapNotNull { neighbor ->
                    getShortestPathToE(neighbor, comingFrom + from)
                }

            val shortestPath = listOfPossibleNeighbors.minByOrNull { it.size }

            return if (shortestPath != null) {
                val adjustedPath = listOf(from) + shortestPath
                this.printPath(adjustedPath)
                this.cache[from] = adjustedPath
                adjustedPath
            }
            else {
                this.cache[from] = null
                null
            }
        }

        fun printPath(coords: List<Square>) {
            val map = Array<Array<Char>>(height) { Array(width) { ' ' } }
            val destination = coords.last()
            coords.dropLast(1).forEachIndexed { index, pair ->
                val neighbor = coords[index + 1]
                val (x1, y1) = pair.coord
                val (x2, y2) = neighbor.coord
                when {
                    x2 > x1 -> map[y1][x1] = '>'
                    x2 < x1 -> map[y1][x1] = '<'
                    y2 > y1 -> map[y1][x1] = 'v'
                    y2 < y1 -> map[y1][x1] = '^'
                }
            }
            map[destination.coord.second][destination.coord.first] = getSignalStrengthAt(destination)
            map.forEach { row ->
                row.forEach { print(it) }
                println()
            }
        }
    }

    fun part1(input: List<String>): Int {
        val area = Area(input.first().length, input.size)
        area.parseSquares(input)

        val shortestPath = area.getShortestPathToE(area.startPosition)
        return if (shortestPath != null) {
            area.printPath(shortestPath)
            shortestPath.size - 1
        }
        else {
            -1
        }
    }

    fun part2(input: List<String>): Int {

        return 0
    }
}
