package tr.emreone.adventofcode

import tr.emreone.adventofcode.days.*
import tr.emreone.utils.Logger.logger
import tr.emreone.utils.Resources

fun main() {

    val day = 1

    logger.info { "Day $day: " }

    when (day) {
        1 -> solveDay1()
        2 -> solveDay2()
// $1
        else -> {
            throw IllegalArgumentException("Day $day is not implemented yet.")
        }
    }

}

fun solveDay1() {
    val input = Resources.resourceAsText(fileName = "day1.txt")

    val solution1 = Day1.part1(input)
    logger.info { "Solution1: $solution1" }

    val solution2 = Day1.part2(input)
    logger.info { "Solution2: $solution2" }
}

fun solveDay2() {
    val input = Resources.resourceAsList(fileName = "day2.txt")

    val solution1 = Day2.part1(input)
    logger.info { "Solution1: $solution1" }

    val solution2 = Day2.part2(input)
    logger.info { "Solution2: $solution2" }
}

// $2
