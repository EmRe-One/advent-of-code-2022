package tr.emreone.adventofcode

import tr.emreone.adventofcode.days.*
import tr.emreone.utils.Logger.logger
import tr.emreone.utils.Resources

class Solutions {

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

    fun solveDay03() {
        val input = Resources.resourceAsList(fileName = "day03.txt")

        val solution1 = Day03.part1(input)
        logger.info { "Solution1: $solution1" }

        val solution2 = Day03.part2(input)
        logger.info { "Solution2: $solution2" }
    }

    fun solveDay04() {
        val input = Resources.resourceAsList(fileName = "day04.txt")

        val solution1 = Day04.part1(input)
        logger.info { "Solution1: $solution1" }

        val solution2 = Day04.part2(input)
        logger.info { "Solution2: $solution2" }
    }

    fun solveDay05() {
        val input = Resources.resourceAsList(fileName = "day05.txt")

        val solution1 = Day05.part1(input)
        logger.info { "Solution1: $solution1" }

        val solution2 = Day05.part2(input)
        logger.info { "Solution2: $solution2" }
    }
// $1

}

fun main() {

    val solution = Solutions()
    val day = 5
    val dayString = day.toString().padStart(2, '0')
    logger.info { "Day $dayString: " }

    try {
        val currentDay = solution.javaClass.getMethod("solveDay$dayString")
        currentDay.invoke(solution)
    } catch (e: Exception) {
        e.printStackTrace()
        logger.error { "Day $dayString is not implemented yet!"}
    }
}
