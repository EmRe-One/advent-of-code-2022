package tr.emreone.adventofcode

import tr.emreone.adventofcode.days.*
import tr.emreone.utils.Logger.logger
import tr.emreone.utils.Resources

class DaySolver {

    fun solveDay01() {
        val input = Resources.resourceAsText(fileName = "day01.txt")

        val solution1 = Day01.part1(input)
        logger.info { "Solution1: $solution1" }

        val solution2 = Day01.part2(input)
        logger.info { "Solution2: $solution2" }
    }

    fun solveDay02() {
        val input = Resources.resourceAsList(fileName = "day02.txt")

        val solution1 = Day02.part1(input)
        logger.info { "Solution1: $solution1" }

        val solution2 = Day02.part2(input)
        logger.info { "Solution2: $solution2" }
    }

// $2

}

fun main() {

    val daySolver = DaySolver()
    val day = 1
    val dayString = day.toString().padStart(2, '0')
    logger.info { "Day $dayString: " }

    try {
        val currentDay = daySolver.javaClass.getMethod("solveDay$dayString")
        currentDay.invoke(daySolver)
    } catch (e: Exception) {
        logger.error { "Day $dayString is not implemented yet!" }
    }
}
