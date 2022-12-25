package tr.emreone.adventofcode.days

object Day25 {

    const val DIGITS = "=-012"

    class SNAFU {
        var decimal: Long = 0L

        fun parseFromSnafu(snafu: String) {
            this.decimal = 0L
            var coeff = 1L

            snafu.reversed().forEach{
                this.decimal += (DIGITS.indexOf(it) - 2) * coeff
                coeff *= 5
            }
        }

        fun printToSnafu(): String {
            var number = this.decimal
            val sb = StringBuilder()

            while (number > 0) {
                val digit = number.mod(5)
                number /= 5
                if (digit in 0..2) {
                    sb.append(digit)
                }
                else {
                    sb.append(DIGITS[digit - 3])
                    number++
                }
            }
            return sb.toString().reversed()
        }

        companion object {
            fun parse(snafu: String): SNAFU {
                return SNAFU().apply {
                    parseFromSnafu(snafu)
                }
            }
        }
    }

    fun part1(input: List<String>): String {
        val total = input.sumOf { SNAFU.parse(it).decimal }

        return SNAFU().apply { decimal = total }.printToSnafu()
    }
}
