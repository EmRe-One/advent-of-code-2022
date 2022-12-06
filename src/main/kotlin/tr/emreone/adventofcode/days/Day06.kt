package tr.emreone.adventofcode.days

object Day06 {

    private fun indexOfMarkerWithNDistinctLetters(msg: String, n: Int): Int {
        return msg.withIndex().windowed(n).indexOfFirst { a: List<IndexedValue<Char>> ->
            a.map { it.value }.toSet().size == n
        }
    }

    fun part1(msg: String): Int {
        val n = 4
        val indexOfMarker = indexOfMarkerWithNDistinctLetters(msg, n)

        return indexOfMarker + n
    }

    fun part2(msg: String): Int {
        val n = 14
        val indexOfMarker = indexOfMarkerWithNDistinctLetters(msg, n)

        return indexOfMarker + n
    }
}
