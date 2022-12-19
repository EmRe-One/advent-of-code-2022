package tr.emreone.adventofcode.days

import tr.emreone.utils.Logger.logger
import tr.emreone.utils.math.Point3D
import java.util.*
import kotlin.math.min
import kotlin.math.max

object Day18 {

    data class Cube(val center: Point3D) {

        fun possibleDirectNeighbors(): List<Cube> {
            return listOf(
                Cube(Point3D(center.x - 1, center.y, center.z)),
                Cube(Point3D(center.x + 1, center.y, center.z)),
                Cube(Point3D(center.x, center.y - 1, center.z)),
                Cube(Point3D(center.x, center.y + 1, center.z)),
                Cube(Point3D(center.x, center.y, center.z - 1)),
                Cube(Point3D(center.x, center.y, center.z + 1))
            )
        }

    }

    class Droplet(input: List<String>) {
        val cubes = input.map { line ->
            val (x, y, z) = line.split(",").map { it.toLong() }
            Cube(Point3D(x, y, z))
        }
        private val outCubes = mutableSetOf<Cube>()
        private val inCubes = mutableSetOf<Cube>()

        fun calcSurfaceArea(): Int {
            return this.cubes.sumOf { cube ->
                val neighbors = cube.possibleDirectNeighbors()

                6 - neighbors.count { this.cubes.contains(it) }
            }
        }

        fun calcOnlyOutsideSurfaceArea(): Int {
            outCubes.clear()
            inCubes.clear()

            fun reachesOutside(cube: Cube): Boolean {
                if (outCubes.contains(cube)) return true
                if (inCubes.contains(cube)) return false

                val seenCubes = mutableSetOf<Cube>()
                val queue = mutableListOf<Cube>(cube)

                while(queue.isNotEmpty()) {
                    val c = queue.removeFirst()
                    if (this.cubes.contains(c))
                        continue

                    if (seenCubes.contains(c))
                        continue

                    seenCubes.add(c)

                    if (seenCubes.size > this.cubes.size) {
                        seenCubes.forEach { outCubes.add(it) }
                        return true
                    }

                    queue.addAll(c.possibleDirectNeighbors())
                }

                seenCubes.forEach { inCubes.add(it) }
                return false
            }

            return this.cubes.sumOf { cube ->
                val neighbors = cube.possibleDirectNeighbors()

                neighbors.count { reachesOutside(it) }
            }
        }
    }

    fun part1(input: List<String>): Int {
        val droplet = Droplet(input)

        return droplet.calcSurfaceArea()
    }

    fun part2(input: List<String>): Int {
        val droplet = Droplet(input)

        return droplet.calcOnlyOutsideSurfaceArea()
    }
}
