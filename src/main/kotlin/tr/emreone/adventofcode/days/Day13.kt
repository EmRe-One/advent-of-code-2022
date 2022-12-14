package tr.emreone.adventofcode.days

import tr.emreone.adventofcode.readTextGroups
import java.util.*

object Day13 {

    private const val OPENING_BRACKET = '['
    private const val CLOSING_BRACKET = ']'

    private fun String.getCommaIndices(): List<Int> {
        val bracketStack = Stack<Int>()
        val commas = mutableListOf<Int>()

        commas.add(-1)
        for (i in indices) {
            when (this[i]) {
                OPENING_BRACKET -> bracketStack.push(i)
                CLOSING_BRACKET -> bracketStack.pop()
                ','             -> if (bracketStack.isEmpty()) commas.add(i)
            }
        }
        commas.add(length)
        return commas
    }

    interface Packet : Comparable<Packet> {
        companion object {
            fun parse(msg: String): Packet {
                val elements = mutableListOf<Packet>()

                val isGroup = msg.startsWith(OPENING_BRACKET) && msg.endsWith(CLOSING_BRACKET)
                if (!isGroup) {
                    return NumberPacket(msg.toInt())
                }

                val droppedMessage = if (isGroup) msg.drop(1).dropLast(1) else msg
                if (droppedMessage.isEmpty()) {
                    return GroupPacket(elements)
                }

                droppedMessage.getCommaIndices().windowed(2).forEach { (start, end) ->
                    val element = droppedMessage.substring(start + 1, end)
                    elements.add(this.parse(element))
                }
                return GroupPacket(elements)
            }
        }

        override fun compareTo(other: Packet): Int {
            if (this is NumberPacket && other is NumberPacket) {
                return this.value.compareTo(other.value)
            }
            else if (this is NumberPacket && other is GroupPacket) {
                return this.convertToGroupPacket().compareTo(other)
            }
            else if (this is GroupPacket && other is NumberPacket) {
                return this.compareTo(other.convertToGroupPacket())
            }
            // both are groups
            val leftGroup = this as GroupPacket
            val rightGroup = other as GroupPacket
            var leftIndex = 0
            while (leftIndex < leftGroup.values.size) {
                // if right group is smaller than left group, then left group is bigger
                if (leftIndex > rightGroup.values.lastIndex) {
                    return 1
                }
                // if left == right then continue
                else if (leftGroup.values[leftIndex] == rightGroup.values[leftIndex]) {
                    leftIndex++
                }
                else {
                    return leftGroup.values[leftIndex].compareTo(rightGroup.values[leftIndex])
                }
            }
            // if code reaches here, then left group is smaller
            return -1
        }
    }

    data class NumberPacket(var value: Int = -1) : Packet {
        fun convertToGroupPacket(): GroupPacket {
            return GroupPacket(listOf(this))
        }
    }

    data class GroupPacket(var values: List<Packet> = emptyList()) : Packet {}

    fun part1(input: String): Int {
        val groups = input.readTextGroups()
            .mapIndexed { index, g ->
                val (left, right) = g.lines()
                index to (Packet.parse(left) to Packet.parse(right))
            }

        return groups.sumOf { (index, pairs) ->
            val (left, right) = pairs
            if (left < right) index + 1 else 0
        }
    }

    fun part2(input: String): Int {
        val elemTwo = Packet.parse("[[2]]")
        val elemSix = Packet.parse("[[6]]")

        val groups = input
            .lines()
            .filter {
                it.isNotBlank()
            }
            .map { g ->
                Packet.parse(g)
            }.toMutableList()
            .also {
                it.add(elemTwo)
                it.add(elemSix)
            }

        // works, because Packets are Comparable
        groups.sort()

        val indexOfTwo = groups.indexOf(elemTwo) + 1
        val indexOfSix = groups.indexOf(elemSix) + 1

        return indexOfTwo * indexOfSix
    }
}
