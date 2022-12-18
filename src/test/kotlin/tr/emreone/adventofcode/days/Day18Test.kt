package tr.emreone.adventofcode.days

import tr.emreone.utils.Resources
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import tr.emreone.utils.math.Point3D

internal class Day18Test {

    @Test
    fun part1() {
        val input = Resources.resourceAsList("day18_example.txt")
        assertEquals(64, Day18.part1(input), "Day18, Part1 should be 64.")
    }

    @Test
    fun part2() {
        val input = Resources.resourceAsList("day18_example.txt")
        assertEquals(58, Day18.part2(input), "Day18, Part2 should be 58.")
    }

}