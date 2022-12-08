package tr.emreone.adventofcode.days

import tr.emreone.utils.Resources
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day07Test {

    @Test
    fun part1() {
        val input = Resources.resourceAsList("day07_example.txt")
        assertEquals(95_437L, Day07.part1(input), "Day07, Part1 should be 95.437.")
    }

    @Test
    fun part2() {
        val input = Resources.resourceAsList("day07_example.txt")
        assertEquals(24_933_642L, Day07.part2(input), "Day07, Part2 should be 24.933.642.")
    }

}