package tr.emreone.adventofcode.days

import org.junit.jupiter.api.Test
import tr.emreone.utils.Resources
import kotlin.test.assertEquals

internal class Day01Test {

    @Test
    fun part1() {
        val input = Resources.resourceAsText("day01_example.txt")
        assertEquals(24000, Day01.part1(input), "Day 1, Part 1 should be 24000.")
    }

    @Test
    fun part2() {
        // same input
        val input = Resources.resourceAsText("day01_example.txt")
        assertEquals(45000, Day01.part2(input), "Day 1, Part 2 should be 45000.")
    }

}