package tr.emreone.adventofcode.days

import java.util.*

object Day04 {

    private val pattern = """(\d+)-(\d+),(\d+)-(\d+)""".toRegex()
    private fun parseRange(line: String): Pair<IntRange, IntRange> {
        val (a, b, c, d) = pattern.matchEntire(line)!!.destructured
        val range1 = IntRange(a.toInt(), b.toInt())
        val range2 = IntRange(c.toInt(), d.toInt())

        return range1 to range2
    }

    operator fun IntRange.contains(other: IntRange): Boolean {
        return this.contains(other.first) && this.contains(other.last)
    }

    private infix fun IntRange.overlaps(other: IntRange): Boolean {
        return this.first <= other.last && other.first <= this.last
    }

    fun part1(input: List<String>): Int {
        return input.count { line ->
            val (range1, range2) = parseRange(line)
            (range1 in range2) || (range2 in range1)
        }
    }

    fun part2(input: List<String>): Int {
        return input.count { line ->
            val (range1, range2) = parseRange(line)
            range1 overlaps range2
        }
    }
}
