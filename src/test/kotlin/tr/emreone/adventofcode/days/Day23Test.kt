package tr.emreone.adventofcode.days

import tr.emreone.utils.Resources
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day23Test {

    @Test
    fun part1() {
        val input = Resources.resourceAsList("day23_example.txt")
        assertEquals(110, Day23.part1(input), "Day23, Part1 should be 110.")
    }

    @Test
    fun part2() {
        val input = Resources.resourceAsList("day23_example.txt")
        assertEquals(-1, Day23.part2(input), "Day23, Part2 should be -1.")
    }

}