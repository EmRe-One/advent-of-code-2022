package tr.emreone.adventofcode.days

import tr.emreone.kotlin_utils.Logger.logger
import tr.emreone.kotlin_utils.math.Point2D
import java.lang.StringBuilder

object Day14 {

    enum class MapElem(val symbol: Char) {
        AIR(' '),
        START_SAND('+'),
        ROCK('#'),
        SAND('·'),
        DROPPING_SAND('~')
    }

    class Terrain {
        private val sandDropStartCoord = Point2D(500, 0)
        private var xMin = 500L
        private var xMax = 500L
        private var yMin = 0L
        private var yMax = 0L

        var numberOfGrainOfSand = 0
        private var currentGrainOfSand: Point2D? = null
        private val terrainMap: MutableMap<Point2D, MapElem> = mutableMapOf()
        var infiniteDepth = true

        companion object {
            fun parse(input: List<String>): Terrain {
                return Terrain().apply {
                    val rocks = input.map { rockDef ->
                        val rockDefParts = mutableSetOf<Point2D>()

                        rockDef.split(" -> ")
                                .map { coords ->
                                    coords.split(",").map { it.toLong() }
                                }
                                .windowed(2)
                                .map {
                                    val (from, to) = it
                                    val fromPoint = Point2D(from[0], from[1])
                                    val toPoint = Point2D(to[0], to[1])

                                    rockDefParts.addAll(fromPoint.lineTo(toPoint))
                                }

                        rockDefParts.toSet()
                    }
                    initMap(rocks)
                }
            }
        }

        private fun setElemAtPoint(point: Point2D, elem: MapElem) {
            this.terrainMap[point] = elem

            // adjust boundaries
            this.xMin = minOf(this.xMin, point.x)
            this.xMax = maxOf(this.xMax, point.x)
            this.yMin = minOf(this.yMin, point.y)
            this.yMax = maxOf(this.yMax, point.y)
        }

        private fun getElemAtPoint(point: Point2D): MapElem {
            return this.terrainMap[point] ?: MapElem.AIR
        }

        private fun initMap(rocks: List<Set<Point2D>>) {
            rocks.forEach { rock ->
                rock.forEach { point ->
                    setElemAtPoint(point, MapElem.ROCK)
                }
            }

            setElemAtPoint(sandDropStartCoord, MapElem.START_SAND)
        }

        fun prepareForPart2() {
            this.infiniteDepth = false
            this.yMax += 2
        }

        fun tick(): Boolean {
            if (currentGrainOfSand == null) {
                currentGrainOfSand = sandDropStartCoord

                if (!this.infiniteDepth && getElemAtPoint(sandDropStartCoord) == MapElem.SAND) {
                    return false
                }

                numberOfGrainOfSand++
            }

            val southWest = Point2D(currentGrainOfSand!!.x - 1, currentGrainOfSand!!.y + 1)
            val south = Point2D(currentGrainOfSand!!.x, currentGrainOfSand!!.y + 1)
            val southEast = Point2D(currentGrainOfSand!!.x + 1, currentGrainOfSand!!.y + 1)

            if (this.infiniteDepth && south.y > yMax) {
                numberOfGrainOfSand--
                return false
            }
            else if (!this.infiniteDepth) {
                if (south.y == yMax) {
                    setElemAtPoint(currentGrainOfSand!!, MapElem.SAND)
                    currentGrainOfSand = null
                    return true
                }
            }

            if (getElemAtPoint(south) == MapElem.AIR) {
                setElemAtPoint(currentGrainOfSand!!, MapElem.AIR)
                setElemAtPoint(south, MapElem.DROPPING_SAND)
                currentGrainOfSand = south
            } else {
                currentGrainOfSand = when (MapElem.AIR) {
                    getElemAtPoint(southWest) -> {
                        setElemAtPoint(currentGrainOfSand!!, MapElem.AIR)
                        setElemAtPoint(southWest, MapElem.DROPPING_SAND)
                        southWest
                    }
                    getElemAtPoint(southEast) -> {
                        setElemAtPoint(currentGrainOfSand!!, MapElem.AIR)
                        setElemAtPoint(southEast, MapElem.DROPPING_SAND)
                        southEast
                    }
                    else -> {
                        setElemAtPoint(currentGrainOfSand!!, MapElem.SAND)
                        null
                    }
                }
            }
            return true
        }

        fun print() {
            val sb = StringBuilder()
            sb.appendLine()
            for (y in (yMin - 1)..(yMax + 1)) {
                if (!this.infiniteDepth && y == yMax) {
                    for (x in (xMin - 1)..(xMax + 1)) {
                        sb.append(MapElem.ROCK.symbol)
                    }
                }
                else {
                    for (x in (xMin - 1)..(xMax + 1)) {
                        sb.append(getElemAtPoint(Point2D(x, y)).symbol)
                    }
                }
                sb.appendLine()
            }
            logger.info { sb.toString() }
        }
    }

    fun part1(input: List<String>): Int {
        val terrain = Terrain.parse(input)

        while (terrain.tick()) {
            // let the sand drop down
            // terrain.print()
        }
        return terrain.numberOfGrainOfSand
    }

    fun part2(input: List<String>): Int {
        val terrain = Terrain.parse(input)
        terrain.prepareForPart2()

        while (terrain.tick()) {
            // let the sand drop down
            // terrain.print()
        }

        return terrain.numberOfGrainOfSand
    }
}
