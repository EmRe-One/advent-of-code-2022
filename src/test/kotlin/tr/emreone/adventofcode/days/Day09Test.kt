package tr.emreone.adventofcode.days

import tr.emreone.utils.Resources
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day09Test {

    @Test
    fun part1() {
        val input = Resources.resourceAsList("day09_example.txt")
        assertEquals(13, Day09.part1(input), "Day09, Part1 should be 13.")
    }

    @Test
    fun part2() {
        val input = Resources.resourceAsList("day09_example.txt")
        assertEquals(-1, Day09.part2(input), "Day09, Part2 should be -1.")
    }

}