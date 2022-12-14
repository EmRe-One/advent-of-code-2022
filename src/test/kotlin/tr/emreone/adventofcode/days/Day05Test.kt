package tr.emreone.adventofcode.days

import tr.emreone.utils.Resources
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day05Test {

    @Test
    fun part1() {
        val input = Resources.resourceAsText("day05_example.txt")
        assertEquals("CMZ", Day05.part1(input), "Day05, Part1 should be 'CMZ'.")
    }

    @Test
    fun part2() {
        val input = Resources.resourceAsText("day05_example.txt")
        assertEquals("MCD", Day05.part2(input), "Day05, Part2 should be 'MCD'.")
    }

}