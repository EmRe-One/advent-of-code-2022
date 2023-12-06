package tr.emreone.adventofcode.days

import kotlinx.coroutines.*
import tr.emreone.adventofcode.manhattanDistanceTo
import tr.emreone.kotlin_utils.Logger.logger
import tr.emreone.kotlin_utils.math.Point2D
import kotlin.math.abs

object Day15 {

    private val REPORT_PATTERN =
        """Sensor at x=(-?\d+), y=(-?\d+): closest beacon is at x=(-?\d+), y=(-?\d+)""".toRegex()

    open class AreaElem(val coord: Point2D)
    class Beacon(coord: Point2D) : AreaElem(coord) {}
    class Sensor(coord: Point2D, val closestBeacon: Beacon) : AreaElem(coord) {
        val distanceToClosestBeacon = coord.manhattanDistanceTo(closestBeacon.coord)
        override fun toString(): String {
            return "Sensor(coord=${coord}, distanceToClosestBeacon=$distanceToClosestBeacon)"
        }


    }

    class Area {
        val beacons = mutableListOf<Beacon>()
        val sensors = mutableListOf<Sensor>()

        companion object {
            fun parseInput(input: List<String>): Area {
                return Area().apply {
                    input.forEach { line ->
                        val (sensorX, sensorY, beaconX, beaconY) = REPORT_PATTERN.matchEntire(line)!!.destructured
                        val beacon = Beacon(Point2D(beaconX.toLong(), beaconY.toLong()))
                        beacons.add(beacon)
                        sensors.add(Sensor(Point2D(sensorX.toLong(), sensorY.toLong()), beacon))
                    }

                    // for part 2
                    sensors.sortBy { it.coord.x }
                }
            }
        }

        fun getMapElemAt(coord: Point2D): AreaElem? {
            sensors.find { it.coord == coord }?.let { return it }
            beacons.find { it.coord == coord }?.let { return it }
            return null
        }

        fun calcTuningFrequency(coord: Point2D): Long {
            return coord.x * 4_000_000 + coord.y
        }

        fun findDistressBeaconInRow(area: Area, yRow: Long, xMin: Long, xMax: Long): Point2D? {
            var x = xMin
            area.sensors.forEach { s ->
                val currentPoint = Point2D(x, yRow)

                // if currentPoint is in a sensor area
                if (s.coord.manhattanDistanceTo(currentPoint) <= s.distanceToClosestBeacon) {
                    x = s.coord.x + s.distanceToClosestBeacon - abs(s.coord.y - yRow) + 1
                }
            }
            return if (x <= xMax)
                Point2D(x, yRow)
            else {
                null
            }
        }

        // Printing methods

        private fun isInSensorArea(coord: Point2D): Boolean {
            return sensors.any { s ->
                coord.manhattanDistanceTo(s.coord) <= s.distanceToClosestBeacon
            }
        }

        fun printMap(min: Long, max: Long) {
            val sb = StringBuilder()
            sb.appendLine()
            (min..max).forEach { y ->
                (min..max).forEach { x ->
                    val currentPoint = Point2D(x, y)
                    when (this.getMapElemAt(currentPoint)) {
                        is Beacon -> {
                            sb.append("B")
                        }

                        is Sensor -> {
                            sb.append("S")
                        }

                        else -> {
                            if (this.isInSensorArea(currentPoint)) {
                                sb.append("#")
                            } else {
                                sb.append(" ")
                            }
                        }
                    }
                }
                sb.appendLine()
            }
            logger.info { sb.toString() }
        }
    }

    fun part1(input: List<String>, yCoord: Long): Int {
        val area = Area.parseInput(input)

        val setOfPossibleCoords = mutableSetOf<Point2D>()

        area.sensors.forEach { s ->
            val xMin = s.coord.x - (s.distanceToClosestBeacon - abs(s.coord.y - yCoord))
            val xMax = s.coord.x + (s.distanceToClosestBeacon - abs(s.coord.y - yCoord))

            if (xMin <= xMax) {
                (xMin..xMax).forEach { x ->
                    // if area is not empty
                    val currentPoint = Point2D(x, yCoord)
                    if (area.getMapElemAt(currentPoint) == null && s.coord.manhattanDistanceTo(currentPoint) <= s.distanceToClosestBeacon) {
                        setOfPossibleCoords.add(currentPoint)
                    }
                }
            }
        }

        // area.printMap(-10, 30)
        return setOfPossibleCoords.size
    }

    fun part2(input: List<String>, minCoord: Long, maxCoord: Long): Long {
        val area = Area.parseInput(input)

        var foundLocation: Point2D? = null

        try {
            runBlocking {
                launch(Dispatchers.Default.limitedParallelism(20)) {
                    (minCoord..maxCoord)
                        .shuffled()
                        .flatMap { y ->
                            listOf(async {
                                if (foundLocation == null) {
                                    val distressBeacon = area.findDistressBeaconInRow(area, y, minCoord, maxCoord)
                                    if (distressBeacon != null) {
                                        logger.debug { "found distress beacon at $distressBeacon, --> Blocking Coroutines ..." }
                                        foundLocation = distressBeacon
                                        this@runBlocking.cancel()
                                    }
                                }
                            })
                        }
                }
            }
        } catch (e: Exception) {
            // do nothing :D
        }

        return foundLocation?.let { area.calcTuningFrequency(it) } ?: -1
    }
}
