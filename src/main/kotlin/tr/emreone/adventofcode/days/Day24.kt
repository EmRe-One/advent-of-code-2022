package tr.emreone.adventofcode.days

import tr.emreone.kotlin_utils.math.Point2D

object Day24 {

    const val MAP_WALL = '#'
    const val MAP_GROUND = '.'

    enum class Blizzard(val symbol: String, dx: Int, dy: Int) {
        UP("^", 0, -1),
        RIGHT(">", 1, 0),
        DOWN("v", 0, 1),
        LEFT("<", -1, 0);
    }

    data class State(var position: Point2D, var minutes: Int) {

    }

    class BlizzardMap (val map: Array<Array<Char>>) {
        val height = map.size
        val width = map.first().size

        val startPosition = Point2D(this.map.first().indexOfFirst { it == MAP_GROUND }.toLong(), 0)
        val endPosition = Point2D(this.map.last().indexOfLast { it == MAP_GROUND }.toLong(), this.height - 1L)

        companion object {
            fun parse(input: List<String>): BlizzardMap {
                val map = input
                    .map {
                        it.toCharArray().toTypedArray()
                    }
                    .toTypedArray()

                return BlizzardMap(map)
            }
        }
    }


    fun part1(input: List<String>): Int {
        val blizzardMap = BlizzardMap.parse(input)

        return 0
    }

    fun part2(input: List<String>): Int {

        return 0
    }
}
