package tr.emreone.adventofcode.days

import tr.emreone.utils.Resources
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import tr.emreone.adventofcode.readTextGroups

internal class Day09Test {

    @Test
    fun part1() {
        val input = Resources.resourceAsText("day09_example.txt").readTextGroups()
        assertEquals(13, Day09.part1(input[0].lines()), "Day09, Part1 should be 13.")
    }

    @Test
    fun part2() {
        val input = Resources.resourceAsText("day09_example.txt").readTextGroups()
        assertEquals(36, Day09.part2(input[1].lines()), "Day09, Part2 should be -1.")
    }

}