package tr.emreone.adventofcode.days

import tr.emreone.utils.Resources
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day20Test {

    @Test
    fun part1() {
        val input = Resources.resourceAsList("day20_example.txt")
        assertEquals(3, Day20.part1(input), "Day20, Part1 should be 3.")
    }

    @Test
    fun part2() {
        val input = Resources.resourceAsList("day20_example.txt")
        assertEquals(1_623_178_306L, Day20.part2(input), "Day20, Part2 should be 1.623.178.306.")
    }

}