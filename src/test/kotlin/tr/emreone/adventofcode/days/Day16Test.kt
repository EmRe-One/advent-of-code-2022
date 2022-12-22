package tr.emreone.adventofcode.days

import tr.emreone.utils.Resources
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day16Test {

    @Test
    fun part1() {
        val input = Resources.resourceAsList("day16_example.txt")
        assertEquals(1651, Day16.part1(input), "Day16, Part1 should be 1651.")
    }

    @Test
    fun part2() {
        val input = Resources.resourceAsList("day16_example.txt")
        assertEquals(1707, Day16.part2(input), "Day16, Part2 should be 1707.")
    }

}