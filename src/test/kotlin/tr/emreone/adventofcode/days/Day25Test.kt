package tr.emreone.adventofcode.days

import tr.emreone.utils.Resources
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day25Test {

    @Test
    fun part1() {
        val input = Resources.resourceAsList("day25_example.txt")
        assertEquals("2=-1=0", Day25.part1(input), "Day25, Part1 should be 4890.")
    }

}