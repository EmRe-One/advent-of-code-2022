package tr.emreone.adventofcode.days

import tr.emreone.utils.Logger.logger

object Day04 {

    operator fun IntRange.contains(other: IntRange): Boolean {
        return this.contains(other.first) && this.contains(other.last)
    }

    private infix fun IntRange.overlaps(other: IntRange): Boolean {
        return this.first <= other.last && other.first <= this.last
    }

    fun part1(input: List<String>): Int {
        val pattern = """(\d+)-(\d+),(\d+)-(\d+)""".toRegex()
        return input.count { line ->
            val (a,b,c,d) = pattern.matchEntire(line)!!.destructured
            val range1 = IntRange(a.toInt(), b.toInt())
            val range2 = IntRange(c.toInt(), d.toInt())

            (range1 in range2) || (range2 in range1)
        }
    }

    fun part2(input: List<String>): Int {
        val pattern = """(\d+)-(\d+),(\d+)-(\d+)""".toRegex()
        return input.count { line ->
            val (a,b,c,d) = pattern.matchEntire(line)!!.destructured
            val range1 = IntRange(a.toInt(), b.toInt())
            val range2 = IntRange(c.toInt(), d.toInt())

            range1 overlaps range2
        }
    }
}
