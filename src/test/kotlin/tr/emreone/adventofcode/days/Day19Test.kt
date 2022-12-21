package tr.emreone.adventofcode.days

import tr.emreone.utils.Resources
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day19Test {

    @Test
    fun part1() {
        val input = Resources.resourceAsList("day19_example.txt")
        assertEquals(33, Day19.part1(input, 24), "Day19, Part1 should be 33.")
    }

    @Test
    fun part2() {
        val input = Resources.resourceAsList("day19_example.txt")
        assertEquals(-1, Day19.part2(input), "Day19, Part2 should be -1.")
    }

}