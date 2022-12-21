package tr.emreone.adventofcode.days

import tr.emreone.utils.Resources
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day21Test {

    @Test
    fun part1() {
        val input = Resources.resourceAsList("day21_example.txt")
        assertEquals(-1, Day21.part1(input), "Day21, Part1 should be -1.")
    }

    @Test
    fun part2() {
        val input = Resources.resourceAsList("day21_example.txt")
        assertEquals(-1, Day21.part2(input), "Day21, Part2 should be -1.")
    }

}