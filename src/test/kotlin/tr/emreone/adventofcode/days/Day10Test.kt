package tr.emreone.adventofcode.days

import tr.emreone.utils.Resources
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day10Test {

    @Test
    fun part1() {
        val input = Resources.resourceAsList("day10_example.txt")
        assertEquals(-1, Day10.part1(input), "Day10, Part1 should be -1.")
    }

    @Test
    fun part2() {
        val input = Resources.resourceAsList("day10_example.txt")
        assertEquals(-1, Day10.part2(input), "Day10, Part2 should be -1.")
    }

}