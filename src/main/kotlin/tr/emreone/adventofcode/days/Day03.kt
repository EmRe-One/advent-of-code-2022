package tr.emreone.adventofcode.days

object Day03 {

    // Lowercase item types a through z have priorities 1 through 26.
    // Uppercase item types A through Z have priorities 27 through 52.
    private fun getPriority(char: Char): Int {
        return if (char.isLowerCase()) {
            char - 'a' + 1
        } else {
            char - 'A' + 27
        }
    }

    fun part1(input: List<String>): Int {
        return input.sumOf {
            val (left, right) =
                it.substring(0, it.length/2) to it.substring(it.length/2, it.length)
            val commonLetter = left.toSet().intersect(right.toSet()).first()

            getPriority(commonLetter)
        }
    }

    fun part2(input: List<String>): Int {
        return input.chunked(3).sumOf {
            val (first, second, third) = it
            val commonLetter = first.toSet().intersect(second.toSet()).intersect(third.toSet()).first()

            getPriority(commonLetter)
        }
    }
}
