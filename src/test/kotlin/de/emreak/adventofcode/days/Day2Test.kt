package de.emreak.adventofcode.days

import tr.emreone.utils.FileLoader
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day2Test {

    @Test
    fun part1() {
        val input = FileLoader.readLines("src/test/resources", "day2_example.txt")
        assertEquals(-1, Day2.part1(input), "Day2, Part1 should be -1.")
    }

    @Test
    fun part2() {
        val input = FileLoader.readLines("src/test/resources", "day2_example.txt")
        assertEquals(-1, Day2.part2(input), "Day2, Part2 should be -1.")
    }

}