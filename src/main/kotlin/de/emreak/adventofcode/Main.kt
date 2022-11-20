package de.emreak.adventofcode

import de.emreak.adventofcode.days.*
import tr.emreone.utils.FileLoader
import tr.emreone.utils.Logger.logger

fun main() {

    val day = 1

    logger.info { "Day $day: " }

    when (day) {
        1 -> solveDay1()
// $1
        else -> {
            throw IllegalArgumentException("Day $day is not implemented yet.")
        }
    }

}

fun solveDay1() {
    val input = FileLoader.readLinesAsInts(filename = "day1.txt")

    val solution1 = Day1.part1(input)
    logger.info {"Solution1: $solution1" }

    val solution2 = Day1.part2(input)
    logger.info {"Solution2: $solution2" }
}

// $2
