package tr.emreone.adventofcode.days

import tr.emreone.utils.Resources
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day02Test {

    @Test
    fun part1() {
        val input = Resources.resourceAsList("day02_example.txt")
        assertEquals(15, Day02.part1(input), "Day2, Part1 should be 15.")
    }

    @Test
    fun part2() {
        val input = Resources.resourceAsList("day02_example.txt")
        assertEquals(12, Day02.part2(input), "Day2, Part2 should be 12.")
    }

}