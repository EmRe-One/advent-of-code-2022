package tr.emreone.adventofcode.days

object Day20 {

    data class MovableIndex(val steps: Long, val sortIndex: Int) {}

    fun part1(input: List<String>): Long {
        val indices = input.mapIndexed { index, line ->
            MovableIndex(line.toLong(), index)
        }.toMutableList()

        val total = indices.size.toLong()
        for (i in 0 until total) {
            val elemIndex = indices.indexOfFirst { it.sortIndex.toLong() == i }
            val elem = indices[elemIndex]

            /*val newIndex = when {
                elemIndex + elem.steps < 0L -> {
                    // move to the end
                    var index = elemIndex + elem.steps + total
                    while(index !in 0 until total) {
                        index += total
                    }
                    index
                }
                elemIndex + elem.steps >= total -> {
                    // move to the beginning
                    var index = elemIndex + elem.steps - total
                    while(index !in 0 until total) {
                        index -= total
                    }
                    index
                }
                elemIndex + elem.steps == 0L -> {
                    total - 1
                }
                else -> {
                    elemIndex + elem.steps
                }
            }*/

            var newIndex = elemIndex + elem.steps
            while (newIndex !in 0 until total) {
                newIndex = if (newIndex < 0) {
                    // move to the end
                    elemIndex + elem.steps + total
                } else {
                    // move to the beginning
                    elemIndex + elem.steps - total
                }
            }

            if (newIndex < elemIndex) {
                indices.add(newIndex.toInt(), elem)
                indices.removeAt(elemIndex + 1)
            } else {
                indices.add(newIndex.toInt() + 1, elem)
                indices.removeAt(elemIndex)
            }
        }

        var countingIndex = indices.indexOfFirst { it.steps == 0L }.toLong()
        println("countingIndex: $countingIndex")
        var sum = 0L
        repeat(3) {
            countingIndex = (countingIndex + 1000L) % total
            sum += indices[countingIndex.toInt()].steps
            println("countingIndex: $countingIndex, sum: $sum")
        }

        return sum
    }

    fun part2(input: List<String>): Int {

        return 0
    }
}
