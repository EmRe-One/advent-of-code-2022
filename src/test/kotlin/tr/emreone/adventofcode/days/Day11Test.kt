package tr.emreone.adventofcode.days

import tr.emreone.utils.Resources
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day11Test {

    @Test
    fun part1() {
        val input = Resources.resourceAsText("day11_example.txt")
        assertEquals(10_605L, Day11.part1(input), "Day11, Part1 should be 10605.")
    }

    @Test
    fun part2() {
        val input = Resources.resourceAsText("day11_example.txt")
        assertEquals(2_713_310_158L, Day11.part2(input), "Day11, Part2 should be 2713310158.")
    }

}