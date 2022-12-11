package tr.emreone.adventofcode.days

import tr.emreone.utils.Resources
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day06Test {

    @Test
    fun part1() {
        val input = Resources.resourceAsList("day06_example.txt")

        input.forEachIndexed { index, value ->
            val (msg, expected1) = value.split(" ")
            assertEquals(expected1.toInt(), Day06.part1(msg), "Day06, Part1:$index should be $expected1.")
        }
    }

    @Test
    fun part2() {
        val input = Resources.resourceAsList("day06_example.txt")

        input.forEachIndexed { index, value ->
            val (msg, _, expected2) = value.split(" ")
            assertEquals(expected2.toInt(), Day06.part2(msg), "Day06, Part2:$index should be $expected2.")
        }
    }

}