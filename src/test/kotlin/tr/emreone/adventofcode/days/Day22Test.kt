package tr.emreone.adventofcode.days

import tr.emreone.utils.Resources
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day22Test {

    @Test
    fun part1() {
        val input = Resources.resourceAsText("day22_example.txt")
        assertEquals(6032, Day22.part1(input), "Day22, Part1 should be 6032.")
    }

    @Test
    fun part2() {
        val input = Resources.resourceAsText("day22_example.txt")
        assertEquals(5031, Day22.part2(input, 4), "Day22, Part2 should be 5031.")
    }

}