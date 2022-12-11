package tr.emreone.adventofcode.days

import tr.emreone.adventofcode.manhattanDistanceTo
import tr.emreone.adventofcode.move
import tr.emreone.utils.math.Point2D

object Day09 {

    private val PATTERN = """(\w+) (\d+)""".toRegex()

    private fun calcNewPositionOfKnot(me: Point2D, nextKnot: Point2D): Point2D {
        val distance = nextKnot.manhattanDistanceTo(me)
        var newPosition = me
        /*
         * if distance is greater than 1, then we have to move tail to new positions
         *
         *     3 2 3
         *   3 2 1 2 3
         *   2 1 M 1 2
         *   3 2 1 2 3
         *     3 2 3
         */
        when (distance) {
            0L, 1L -> {
                // do nothing
            }

            else -> {
                // distance 2: if share one axis then move tail to head
                if (distance == 2L && !nextKnot.sharesAxisWith(me)) {
                    // head is direct diagonal to tail and do nothing
                } else {
                    val xDirection = (nextKnot.x - me.x).coerceIn(-1, 1)
                    val yDirection = (nextKnot.y - me.y).coerceIn(-1, 1)
                    newPosition = Point2D(me.x + xDirection, me.y + yDirection)
                }
            }
        }
        return newPosition
    }

    private fun trackVisitedCoordsOfTail(input: List<String>, snakeLength: Int = 2): Set<Point2D> {
        val snakeKnots = mutableListOf<Pair<Point2D, List<Point2D>>>()

        for(i in 0 until snakeLength) {
            snakeKnots.add(Point2D(0, 0) to listOf(Point2D(0, 0)))
        }

        input.forEach {
            val (direction, distance) = PATTERN.matchEntire(it)!!.destructured
            for (i in 1..distance.toInt()) {
                val newHeadPosition = snakeKnots[0].first.move(direction, 1)
                snakeKnots[0] = newHeadPosition to (snakeKnots[0].second + newHeadPosition)

                for(k in 1 until snakeKnots.size) {

                    val prevKnot = snakeKnots[k - 1].first
                    val me = snakeKnots[k].first

                    val newTailPosition = calcNewPositionOfKnot(me, prevKnot)
                    snakeKnots[k] = newTailPosition to (snakeKnots[k].second + newTailPosition)
                }
            }
        }

        return snakeKnots.last().second.toSet()
    }

    fun part1(input: List<String>): Int {
        return trackVisitedCoordsOfTail(input).size
    }

    fun part2(input: List<String>): Int {
        return trackVisitedCoordsOfTail(input, 10).size
    }
}
