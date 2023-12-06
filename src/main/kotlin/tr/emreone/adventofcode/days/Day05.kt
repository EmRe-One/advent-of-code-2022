package tr.emreone.adventofcode.days

import tr.emreone.adventofcode.readTextGroups
import tr.emreone.kotlin_utils.Logger.logger
import java.util.Stack

object Day05 {

    private val pattern = "move (\\d+) from (\\d+) to (\\d+)".toRegex()

    private fun printCrates(crates: List<Stack<Char>>) {
        val sb = StringBuilder()
        crates.forEachIndexed { index, crate ->
            sb.append("Crate $index: ")
            crate.forEach { sb.append("$it ") }
            sb.appendLine()
        }
        logger.info { sb.toString() }
    }

    private fun stackOf(input: String): Stack<Char> {
        val stack = Stack<Char>()
        input.forEach { stack.push(it) }
        return stack
    }
    private fun stackOf(vararg elements: Char): Stack<Char> {
        val stack = Stack<Char>()
        elements.forEach { stack.push(it) }
        return stack
    }


    //                         111111...
    // index:        0123456789012345...
    //               [Z]·········[C]·...
    // distribution: [X]·[Y]·[A]·[B]·...
    // cratesIds:    ·1···2···3···4··...
    private fun parseStackOfCrates(input: List<String>): List<Stack<Char>> {
        val cratesLine = input.last()
        val stackOfCrates = mutableListOf<Stack<Char>>()
        """(\d+)""".toRegex().findAll(cratesLine).forEach { _ ->
            stackOfCrates.add(stackOf())
        }

        val stacksDistribution = input.dropLast(1).reversed()
        stacksDistribution.forEach { row ->
            val lineLength = row.length
            stackOfCrates.forEachIndexed { index, crate ->
                val crateIdIndex = (index * 4) + 1
                if (crateIdIndex < lineLength) {
                    val crateId = row[crateIdIndex]
                    if (crateId != ' ') {
                        crate.push(crateId)
                    }
                }
            }
        }

        return stackOfCrates
    }

    private fun solveCrateMovements(input: String, moveOneByOne: Boolean = true): String {
        // TODO split text into header and movements
        val (header, movements) = input.readTextGroups()

        val stackOfCrates = parseStackOfCrates(header.lines())

        movements.lines().forEach {
            val (no, from, to) = pattern.matchEntire(it)!!.destructured

            if (moveOneByOne) {
                for (i in 0 until no.toInt()) {
                    stackOfCrates[to.toInt() - 1].push(stackOfCrates[from.toInt() - 1].pop())
                }
            }
            else {
                val temp = Stack<Char>()
                for (i in 0 until no.toInt()) {
                    temp.push(stackOfCrates[from.toInt() - 1].pop())
                }
                for (i in 0 until no.toInt()) {
                    stackOfCrates[to.toInt() - 1].push(temp.pop())
                }
            }
        }

        return stackOfCrates.map { it.last() }.joinToString("")
    }

    fun part1(input: String): String {
        return solveCrateMovements(input, true)
    }

    fun part2(input: String): String {
        return solveCrateMovements(input, false)
    }
}
