package tr.emreone.adventofcode.days

import tr.emreone.utils.Resources
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day24Test {

    @Test
    fun part1() {
        val input = Resources.resourceAsList("day24_example.txt")
        assertEquals(18, Day24.part1(input), "Day24, Part1 should be 18.")
    }

    @Test
    fun part2() {
        val input = Resources.resourceAsList("day24_example.txt")
        assertEquals(54, Day24.part2(input), "Day24, Part2 should be 54.")
    }

}