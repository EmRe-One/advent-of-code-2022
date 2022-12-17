package tr.emreone.adventofcode.days

import tr.emreone.utils.Resources
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day17Test {

    @Test
    fun part1() {
        val input = Resources.resourceAsText("day17_example.txt")
        assertEquals(3068, Day17.part1(input.trim(), 20), "Day17, Part1 should be -1.")
    }

    @Test
    fun part2() {
        val input = Resources.resourceAsText("day17_example.txt")
        assertEquals(-1, Day17.part2(input), "Day17, Part2 should be -1.")
    }

}