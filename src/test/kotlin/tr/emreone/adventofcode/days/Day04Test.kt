package tr.emreone.adventofcode.days

import tr.emreone.utils.Resources
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day04Test {

    @Test
    fun part1() {
        val input = Resources.resourceAsList("day04_example.txt")
        assertEquals(2, Day04.part1(input), "Day04, Part1 should be 2.")
    }

    @Test
    fun part2() {
        val input = Resources.resourceAsList("day04_example.txt")
        assertEquals(4, Day04.part2(input), "Day04, Part2 should be 4.")
    }

}