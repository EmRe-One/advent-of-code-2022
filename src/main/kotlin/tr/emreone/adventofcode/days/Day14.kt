package tr.emreone.adventofcode.days

import tr.emreone.utils.Logger.logger
import tr.emreone.utils.math.Point2D
import java.lang.Long
import java.lang.StringBuilder

object Day14 {

    enum class MapElem(val symbol: Char) {
        AIR('·'),
        START_SAND('+'),
        ROCK('#'),
        SAND('o'),
        DROPPING_SAND('~')
    }

    class Terrain {
        private val X_BORDER = 1000
        private val sandDropStartCoord = Point2D(500, 0)
        private var xMin = 500L
        private var xMax = 500L
        private var yMin = 0L
        private var yMax = 0L

        var numberOfGrainOfSand = 0
        private var currentGrainOfSand: Point2D? = null
        private lateinit var terrainMap: Array<Array<MapElem>>
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
            this.terrainMap[(point.y - yMin).toInt() + 1][(point.x - xMin).toInt() + X_BORDER/2] = elem
        }
        private fun getElemAtPoint(point: Point2D): MapElem {
            return this.terrainMap
                    .getOrNull((point.y - yMin).toInt() + 1)
                    ?.getOrNull((point.x - xMin).toInt() + X_BORDER/2) ?: MapElem.AIR
        }

        private fun initMap(rocks: List<Set<Point2D>>) {
            this.xMin = Long.min(this.xMin, rocks.minOf { rock -> rock.minOf { it.x } })
            this.xMax = Long.max(this.xMax, rocks.maxOf { rock -> rock.maxOf { it.x } })
            this.yMin = Long.min(this.yMin, rocks.minOf { rock -> rock.minOf { it.y } })
            this.yMax = Long.max(this.yMax, rocks.maxOf { rock -> rock.maxOf { it.y } })

            terrainMap = Array((yMax - yMin).toInt() + 3) {
                Array((xMax - xMin).toInt() + X_BORDER+1) { MapElem.AIR }
            }

            rocks.forEach { rock ->
                rock.forEach { point ->
                    setElemAtPoint(point, MapElem.ROCK)
                }
            }

            setElemAtPoint(sandDropStartCoord, MapElem.START_SAND)
        }

        fun tick(): Boolean {
            if (currentGrainOfSand == null) {
                currentGrainOfSand = sandDropStartCoord
                numberOfGrainOfSand++
            }

            val nextPoint = Point2D(currentGrainOfSand!!.x, currentGrainOfSand!!.y + 1)
            // as long as there is a sand corn smaller than yMax, sand drops
            if (this.infiniteDepth) {
                if (nextPoint.y > yMax) {
                    numberOfGrainOfSand--
                    return false
                }
            }
            else {
                if (nextPoint.y > yMax) {
                    setElemAtPoint(currentGrainOfSand!!, MapElem.SAND)
                    currentGrainOfSand = null
                    return true
                }
            }

            if (getElemAtPoint(nextPoint) == MapElem.AIR) {
                setElemAtPoint(currentGrainOfSand!!, MapElem.AIR)
                setElemAtPoint(nextPoint, MapElem.DROPPING_SAND)
                currentGrainOfSand = nextPoint
            }
            else {
                val downLeftPoint = Point2D(currentGrainOfSand!!.x - 1, currentGrainOfSand!!.y + 1)
                val downRightPoint = Point2D(currentGrainOfSand!!.x + 1, currentGrainOfSand!!.y + 1)

                if (getElemAtPoint(downLeftPoint) == MapElem.AIR) {
                    setElemAtPoint(currentGrainOfSand!!, MapElem.AIR)
                    setElemAtPoint(downLeftPoint, MapElem.DROPPING_SAND)
                    currentGrainOfSand = downLeftPoint
                }
                else if (getElemAtPoint(downRightPoint) == MapElem.AIR) {
                    setElemAtPoint(currentGrainOfSand!!, MapElem.AIR)
                    setElemAtPoint(downRightPoint, MapElem.DROPPING_SAND)
                    currentGrainOfSand = downRightPoint
                }
                else {
                    setElemAtPoint(currentGrainOfSand!!, MapElem.SAND)
                    currentGrainOfSand = null
                    if (!infiniteDepth && currentGrainOfSand == sandDropStartCoord) {
                        return false
                    }
                }
            }
            return true
        }

        fun print() {
            val sb = StringBuilder()
            sb.appendLine()
            terrainMap.forEach { row ->
                row.forEach { cell ->
                    sb.append(cell.symbol)
                }
                sb.appendLine()
            }
            logger.info { sb.toString() }
        }
    }

    fun part1(input: List<String>): Int {
        val terrain = Terrain.parse(input)

        // terrain.print()
        while (terrain.tick()) {
            // let the sand drop down
            // terrain.print()
        }
        // terrain.print()
        return terrain.numberOfGrainOfSand
    }

    fun part2(input: List<String>): Int {
        val terrain = Terrain.parse(input)
        terrain.infiniteDepth = false

        while (terrain.tick()) {
            // let the sand drop down
            // terrain.print()
        }


        return terrain.numberOfGrainOfSand
        return 0
    }
}
