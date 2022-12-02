package tr.emreone.adventofcode.days

object Day01 {

    private fun collectCaloriesOfElves(input: String): List<Int> {
        return input.split("\n\n").map { elves ->
            elves.split("\n")
                .sumOf { it.toInt() }
        }
    }

    fun part1(input: String): Int {
        return collectCaloriesOfElves(input).max()
    }

    fun part2(input: String): Int {
        return collectCaloriesOfElves(input).sortedDescending().take(3).sum();
    }

}
