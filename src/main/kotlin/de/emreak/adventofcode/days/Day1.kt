package de.emreak.adventofcode.days

object Day1 {

    private fun collectCaloriesOfElves(input: String): List<Int> {
        return input.split("\n\n").map { elves ->
            elves.split("\n")
                .sumOf { it.toInt() }
        }
    }

    fun part1(list:String): Int {
        return collectCaloriesOfElves(list).max()
    }

    fun part2(list: String): Int {
        return collectCaloriesOfElves(list).sortedDescending().take(3).sum();
    }

}
