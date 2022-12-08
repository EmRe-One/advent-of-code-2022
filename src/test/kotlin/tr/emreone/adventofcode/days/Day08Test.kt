package tr.emreone.adventofcode.days

import tr.emreone.utils.Resources
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day08Test {

    @Test
    fun part1() {
        val input = Resources.resourceAsList("day08_example.txt")
        assertEquals(21, Day08.part1(input), "Day08, Part1 should be 21.")
    }

    @Test
    fun part2() {
        val input = Resources.resourceAsList("day08_example.txt")
        assertEquals(8, Day08.part2(input), "Day08, Part2 should be 8.")
    }

}