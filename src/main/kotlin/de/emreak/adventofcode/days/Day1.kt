package de.emreak.adventofcode.days

object Day1 {

    private fun collectCaloriesOfElfes(input: List<String>): MutableList<List<Int>> {
        val elfList = mutableListOf<List<Int>>()
        val currentElf = mutableListOf<Int>()

        input.forEach {
            if (it == "") {
                elfList.add(currentElf.toList())
                currentElf.clear()
            } else {
                currentElf.add(it.toInt())
            }
        }
        if (currentElf.isNotEmpty()) {
            elfList.add(currentElf.toList())
        }
        return elfList
    }

    fun part1(list: List<String>): Int {
        return collectCaloriesOfElfes(list).maxOf { it.sum() }
    }

    fun part2(list: List<String>): Int {
        val elfList = collectCaloriesOfElfes(list)
        elfList.sortByDescending { it.sum() }
        return elfList.take(3).sumOf { it.sum() }
    }

}
