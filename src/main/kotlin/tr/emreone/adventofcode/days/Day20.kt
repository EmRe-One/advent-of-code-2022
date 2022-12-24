package tr.emreone.adventofcode.days

import java.util.Collections.swap
import kotlin.math.abs

object Day20 {

    class Shuffler(val numbers: MutableList<IndexedValue<Long>>) {

        fun shuffle(times: Int, decryptKey: Long = 1L): Long {
            val size = numbers.size

            for(index in 0 until (size * times)) {
                var i = this.numbers.indexOfFirst { it.index == index % size }
                val v  = (this.numbers[i].value * decryptKey % (size - 1)).toInt()

                repeat(abs(v)) {
                    val newI = (i + v / abs(v)).mod(size)
                    swap(this.numbers, i, newI)
                    i = newI
                }
            }

            val indexOfZero = this.numbers.indexOfFirst { it.value == 0L }

            return (indexOfZero..(indexOfZero + 3000) step 1000).sumOf {
                this.numbers[it % size].value * decryptKey
            }
        }

        companion object {
            fun parse(input: List<String>): Shuffler {
                val list = input
                    .map { it.toLong() }
                    .withIndex()
                    .toMutableList()

                return Shuffler(list)
            }
        }
    }

    fun part1(input: List<String>): Long {
        val shuffler = Shuffler.parse(input)
        return shuffler.shuffle(1)
    }

    fun part2(input: List<String>): Long {
        val shuffler = Shuffler.parse(input)
        return shuffler.shuffle(10, 811_589_153L)
    }
}
