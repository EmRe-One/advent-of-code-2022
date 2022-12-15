package tr.emreone.adventofcode.days

import tr.emreone.utils.Resources
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day15Test {

    @Test
    fun part1() {
        val input = Resources.resourceAsList("day15_example.txt")
        assertEquals(26, Day15.part1(input, 10L), "Day15, Part1 should be 26.")
    }

    @Test
    fun part2() {
        val input = Resources.resourceAsList("day15_example.txt")
        assertEquals(56_000_011L, Day15.part2(input, 0L, 20L), "Day15, Part2 should be 56000011.")
    }

}