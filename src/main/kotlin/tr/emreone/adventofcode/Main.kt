package tr.emreone.adventofcode

import tr.emreone.adventofcode.days.*
import tr.emreone.utils.Logger.logger
import tr.emreone.utils.Resources
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

@OptIn(ExperimentalTime::class)
class Solutions {

    /**
     * day is the day nummer of the puzzle.
     *
     * part is the part of the puzzle.
     * - 01 = 1 -> for only part 1
     * - 10 = 2 -> for only part 2 or
     * - 11 = 3 -> for both day parts
     */
    fun solveDay(day: Int, part: Int = 3) {
        val dayString = day.toString().padStart(2, '0')
        val dayClass = Class.forName("tr.emreone.adventofcode.days.Day$dayString")

        if (part.and(0b01) > 0) {
            val part1 = dayClass.getMethod("part1", List::class.java)
            val input = Resources.resourceAsList("day${dayString}.txt")
            val (result, duration) = measureTimedValue { part1.invoke(null, input) }

            logger.info("Day$dayString, Part1 solve in $duration:")
            logger.info { result  }
        }
        if (part.and(0b10) > 0) {
            val part2 = dayClass.getMethod("part2", List::class.java)
            val input = Resources.resourceAsList("day${dayString}.txt")
            val (result, duration) = measureTimedValue { part2.invoke(null, input) }

            logger.info("Day$dayString, Part2 solve in $duration:")
            logger.info { result  }
        }
    }

    fun solveDay01() {
        val input = Resources.resourceAsText(fileName = "day01.txt")

        val (part1, elapsedTime1) = measureTimedValue {
            Day01.part1(input)
        }
        logger.info { "Part1 solved in $elapsedTime1:" }
        logger.info { part1 }

        val (part2, elapsedTime2) = measureTimedValue {
            Day01.part2(input)
        }
        logger.info { "Part2 solved in $elapsedTime2:" }
        logger.info { part2 }
    }
    fun solveDay02() {
        val input = Resources.resourceAsList(fileName = "day02.txt")

        val (part1, elapsedTime1) = measureTimedValue {
            Day02.part1(input)
        }
        logger.info { "Part1 solved in $elapsedTime1:" }
        logger.info { part1 }

        val (part2, elapsedTime2) = measureTimedValue {
            Day02.part2(input)
        }
        logger.info { "Part2 solved in $elapsedTime2:" }
        logger.info { part2 }
    }
    fun solveDay03() {
        val input = Resources.resourceAsList(fileName = "day03.txt")

        val (part1, elapsedTime1) = measureTimedValue {
            Day03.part1(input)
        }
        logger.info { "Part1 solved in $elapsedTime1:" }
        logger.info { part1 }

        val (part2, elapsedTime2) = measureTimedValue {
            Day03.part2(input)
        }
        logger.info { "Part2 solved in $elapsedTime2:" }
        logger.info { part2 }
    }
    fun solveDay04() {
        val input = Resources.resourceAsList(fileName = "day04.txt")

        val (part1, elapsedTime1) = measureTimedValue {
            Day04.part1(input)
        }
        logger.info { "Part1 solved in $elapsedTime1:" }
        logger.info { part1 }

        val (part2, elapsedTime2) = measureTimedValue {
            Day04.part2(input)
        }
        logger.info { "Part2 solved in $elapsedTime2:" }
        logger.info { part2 }
    }
    fun solveDay05() {
        val input = Resources.resourceAsText(fileName = "day05.txt")

        val (part1, elapsedTime1) = measureTimedValue {
            Day05.part1(input)
        }
        logger.info { "Part1 solved in $elapsedTime1:" }
        logger.info { part1 }

        val (part2, elapsedTime2) = measureTimedValue {
            Day05.part2(input)
        }
        logger.info { "Part2 solved in $elapsedTime2:" }
        logger.info { part2 }
    }
    fun solveDay06() {
        val input = Resources.resourceAsList(fileName = "day06.txt")

        val (part1, elapsedTime1) = measureTimedValue {
            Day06.part1(input.first())
        }
        logger.info { "Part1 solved in $elapsedTime1:" }
        logger.info { part1 }

        val (part2, elapsedTime2) = measureTimedValue {
            Day06.part2(input.first())
        }
        logger.info { "Part2 solved in $elapsedTime2:" }
        logger.info { part2 }
    }
    fun solveDay07() {
        val input = Resources.resourceAsList(fileName = "day07.txt")

        val (part1, elapsedTime1) = measureTimedValue {
            Day07.part1(input)
        }
        logger.info { "Part1 solved in $elapsedTime1:" }
        logger.info { part1 }

        val (part2, elapsedTime2) = measureTimedValue {
            Day07.part2(input)
        }
        logger.info { "Part2 solved in $elapsedTime2:" }
        logger.info { part2 }
    }
    fun solveDay08() {
        val input = Resources.resourceAsList(fileName = "day08.txt")

        val (part1, elapsedTime1) = measureTimedValue {
            Day08.part1(input)
        }
        logger.info { "Part1 solved in $elapsedTime1:" }
        logger.info { part1 }

        val (part2, elapsedTime2) = measureTimedValue {
            Day08.part2(input)
        }
        logger.info { "Part2 solved in $elapsedTime2:" }
        logger.info { part2 }
    }
    fun solveDay09() {
        val input = Resources.resourceAsList(fileName = "day09.txt")

        val (part1, elapsedTime1) = measureTimedValue {
            Day09.part1(input)
        }
        logger.info { "Part1 solved in $elapsedTime1:" }
        logger.info { part1 }

        val (part2, elapsedTime2) = measureTimedValue {
            Day09.part2(input)
        }
        logger.info { "Part2 solved in $elapsedTime2:" }
        logger.info { part2 }
    }
    fun solveDay10() {
        val input = Resources.resourceAsList(fileName = "day10.txt")

        val (part1, elapsedTime1) = measureTimedValue {
            Day10.part1(input)
        }
        logger.info { "Part1 solved in $elapsedTime1:" }
        logger.info { part1 }

        val (part2, elapsedTime2) = measureTimedValue {
            Day10.part2(input)
        }
        logger.info { "Part2 solved in $elapsedTime2:" }
        logger.info { part2 }
    }
    fun solveDay11() {
        val input = Resources.resourceAsText(fileName = "day11.txt")

        val (part1, elapsedTime1) = measureTimedValue {
            Day11.part1(input)
        }
        logger.info { "Part1 solved in $elapsedTime1:" }
        logger.info { part1 }

        val (part2, elapsedTime2) = measureTimedValue {
            Day11.part2(input)
        }
        logger.info { "Part2 solved in $elapsedTime2:" }
        logger.info { part2 }
    }
    fun solveDay12() {
        val input = Resources.resourceAsList(fileName = "day12.txt")

        val (part1, elapsedTime1) = measureTimedValue {
            Day12.part1(input)
        }
        logger.info { "Part1 solved in $elapsedTime1:" }
        logger.info { part1 }

        val (part2, elapsedTime2) = measureTimedValue {
            Day12.part2(input)
        }
        logger.info { "Part2 solved in $elapsedTime2:" }
        logger.info { part2 }
    }
    fun solveDay13() {
        val input = Resources.resourceAsText(fileName = "day13.txt")

        val (part1, elapsedTime1) = measureTimedValue {
            Day13.part1(input)
        }
        logger.info { "Part1 solved in $elapsedTime1:" }
        logger.info { part1 }

        val (part2, elapsedTime2) = measureTimedValue {
            Day13.part2(input)
        }
        logger.info { "Part2 solved in $elapsedTime2:" }
        logger.info { part2 }
    }
    fun solveDay14() {
        val input = Resources.resourceAsList(fileName = "day14.txt")

        val (part1, elapsedTime1) = measureTimedValue {
            Day14.part1(input)
        }
        logger.info { "Part1 solved in $elapsedTime1:" }
        logger.info { part1 }

        val (part2, elapsedTime2) = measureTimedValue {
            Day14.part2(input)
        }
        logger.info { "Part2 solved in $elapsedTime2:" }
        logger.info { part2 }
    }
    fun solveDay15() {
        val input = Resources.resourceAsList(fileName = "day15.txt")

        val (part1, elapsedTime1) = measureTimedValue {
            Day15.part1(input, 2_000_000L)
        }
        logger.info { "Part1 solved in $elapsedTime1:" }
        logger.info { part1 }

        val (part2, elapsedTime2) = measureTimedValue {
            Day15.part2(input, 0L, 4_000_000L)
        }
        logger.info { "Part2 solved in $elapsedTime2:" }
        logger.info { part2 }
    }
    fun solveDay16() {
        val input = Resources.resourceAsList(fileName = "day16.txt")

        val (part1, elapsedTime1) = measureTimedValue {
            Day16.part1(input)
        }
        logger.info { "Part1 solved in $elapsedTime1:" }
        logger.info { part1 }

        val (part2, elapsedTime2) = measureTimedValue {
            Day16.part2(input)
        }
        logger.info { "Part2 solved in $elapsedTime2:" }
        logger.info { part2 }
    }
    fun solveDay17() {
        val input = Resources.resourceAsText(fileName = "day17.txt")

        val (part1, elapsedTime1) = measureTimedValue {
            Day17.part1(input.trim(), 2022)
        }
        logger.info { "Part1 solved in $elapsedTime1:" }
        logger.info { part1 }

        val (part2, elapsedTime2) = measureTimedValue {
            Day17.part2(input.trim())
        }
        logger.info { "Part2 solved in $elapsedTime2:" }
        logger.info { part2 }
    }
    fun solveDay18() {
        val input = Resources.resourceAsList(fileName = "day18.txt")

        val (part1, elapsedTime1) = measureTimedValue {
            Day18.part1(input);
        }
        logger.info { "Part1 solved in $elapsedTime1:" }
        logger.info { part1 }

        val (part2, elapsedTime2) = measureTimedValue {
            Day18.part2(input);
        }
        logger.info { "Part2 solved in $elapsedTime2:" }
        logger.info { part2 }
    }
// $1

}

fun main() {
    val solution = Solutions()
    val day = 12
    val dayString = day.toString().padStart(2, '0')
    logger.info { "Solving Puzzles for Day $dayString: " }

    try {
        val currentDay = solution.javaClass.getMethod("solveDay$dayString")
        currentDay.invoke(solution)
    } catch (e: Exception) {
        e.printStackTrace()
        logger.error { "Day $dayString is not implemented yet!" }
    }
}
