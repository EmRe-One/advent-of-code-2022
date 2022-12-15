package tr.emreone.adventofcode.days

import tr.emreone.utils.Logger.logger
import java.lang.StringBuilder

object Day10 {

    private val PATTERN = """(noop|addx\s)(-?\d+)?""".toRegex()

    // calculate signal strength at 20th, 60th, 100th, 140th, 180th, and 220th cycle
    fun part1(input: List<String>): Int {
        var registerValue = 1
        var cycle = 0
        val signalStrengths = mutableListOf<Int>()

        fun adjustSignalStrength(cycle: Int, registerValue: Int) {
            if (cycle in 20..220 step 40) {
                signalStrengths.add(cycle * registerValue)
            }
        }

        input.forEach { command ->
            val (operation, value) = PATTERN.matchEntire(command)!!.destructured

            when (operation.trim()) {
                "noop" -> {
                    cycle++
                    adjustSignalStrength(cycle, registerValue)
                }

                "addx" -> {
                    cycle++
                    adjustSignalStrength(cycle, registerValue)
                    cycle++
                    adjustSignalStrength(cycle, registerValue)
                    registerValue += value.toInt()
                }
            }
        }

        return signalStrengths.sumOf { it }
    }

    fun printCrt(input: Array<CharArray>) {
        logger.info("Printing CRT")
        input.forEach { row ->
            val sb = StringBuilder()
            row.forEach { pixel ->
                sb.append(pixel)
            }
            logger.info { sb.toString() }
        }
    }

    fun part2(input: List<String>): String {
        val WIDTH_OF_CRT = 40
        val HEIGHT_OF_CRT = 6
        val crt = Array(HEIGHT_OF_CRT) { CharArray(WIDTH_OF_CRT) { ' ' } }

        var indexOfSprite = 1
        var cycle = 0

        fun drawPixel(cycle: Int, indexOfSprite: Int) {
            val (x, y) = cycle % WIDTH_OF_CRT to cycle / WIDTH_OF_CRT
            if ((cycle % WIDTH_OF_CRT) in ((indexOfSprite - 1)..(indexOfSprite + 1))) {
                crt[y][x] = '#'
            }
        }

        input.forEach { command ->
            val (operation, value) = PATTERN.matchEntire(command)!!.destructured

            when (operation.trim()) {
                "noop" -> {
                    drawPixel(cycle, indexOfSprite)
                    cycle++
                }
                "addx" -> {
                    drawPixel(cycle, indexOfSprite)
                    cycle++
                    drawPixel(cycle, indexOfSprite)
                    cycle++
                    indexOfSprite += value.toInt()
                }
            }
            // printCrt(crt)
        }

        val sb = StringBuilder()
        sb.appendLine()
        crt.forEach { row ->
            row.forEach { pixel ->
                sb.append(pixel)
            }
            sb.appendLine()
        }
        return sb.toString()
    }
}
