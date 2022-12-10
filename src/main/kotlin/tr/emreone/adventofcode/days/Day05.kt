package tr.emreone.adventofcode.days

import tr.emreone.utils.Logger.logger
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


    //                    111111...
    // index:   0123456789012345...
    //           ...............
    // crateId: [A]·[B]·[C]·[D]·...
    // crates:  ·1···2···3···4··...
    private fun parseStackOfCrates(input: List<String>): Pair<List<Stack<Char>>, Int> {
        val stackOfCrates = mutableListOf<Stack<Char>>()
        val indexLinePattern = "(\\s(\\d+)\\s?)+".toRegex()
        // TODO replace with last line of header

        val cratesLineIndex = input.indexOfFirst { indexLinePattern.containsMatchIn(it) }
        indexLinePattern.findAll(input[cratesLineIndex]).forEach { _ ->
            stackOfCrates.add(stackOf())
        }

        for(i in (cratesLineIndex - 1) downTo 0) {
            val currentLine = input[i]
            val lineLength = currentLine.length
            stackOfCrates.forEachIndexed { index, crate ->
                val crateIdIndex = (index * 4) + 1
                if (crateIdIndex < lineLength) {
                    val crateId = currentLine[crateIdIndex]
                    if (crateId != ' ') {
                        crate.push(crateId)
                    }
                }
            }
        }

        return stackOfCrates to cratesLineIndex
    }

    private fun solveCrateMovements(input: List<String>, moveOneByOne: Boolean = true): String {
        // TODO split text into header and movements
        val (stackOfCrates, startRow) = parseStackOfCrates(input)

        input.subList(startRow + 2, input.size).forEach {
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

    fun part1(input: List<String>): String {
        return solveCrateMovements(input, true)
    }

    fun part2(input: List<String>): String {
        return solveCrateMovements(input, false)
    }
}
