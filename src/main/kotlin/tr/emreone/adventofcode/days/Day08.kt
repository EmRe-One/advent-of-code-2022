package tr.emreone.adventofcode.days

import tr.emreone.utils.Logger.logger
import tr.emreone.utils.math.Coords

object Day08 {

    private fun generateTreeMap(input: List<String>): Map<Coords, List<List<Int>>> {
        val trees = mutableMapOf<Coords, List<List<Int>>>()

        input.forEachIndexed { y, line ->
            line.forEachIndexed { x, _ ->
                if (y > 0 && y < input.lastIndex && x > 0 && x < line.lastIndex) {
                    val top = IntRange(0, y - 1).map { yy -> input[yy][x].digitToInt() }
                    val right = IntRange(x + 1, line.lastIndex).map { xx -> input[y][xx].digitToInt() }
                    val bottom = IntRange(y + 1, input.lastIndex).map { yy -> input[yy][x].digitToInt() }
                    val left = IntRange(0, x - 1).map { xx -> input[y][xx].digitToInt() }

                    trees[x to y] = listOf(top.reversed(), right, bottom, left.reversed())
                } else {
                    // ignore trees on the edge
                    trees[x to y] = emptyList()
                }
            }
        }
        return trees
    }

    private fun isOnTheEdge(input: List<String>, coords: Coords): Boolean {
        return coords.first == 0
                || coords.first == input.first().lastIndex
                || coords.second == 0
                || coords.second == input.lastIndex
    }

    fun part1(input: List<String>): Int {

        val trees = generateTreeMap(input)

        // trees on the edge
        var visibleTrees = trees.count {(key, _) ->
            isOnTheEdge(input, key)
        }

        visibleTrees += trees.filter { !isOnTheEdge(input, it.key) }
            .count { (t, u) ->
                val current = input[t.second][t.first].digitToInt()
                u.count { it.all { n -> n < current } } > 0
            }

        return visibleTrees
    }

    fun part2(input: List<String>): Int {

        val trees = generateTreeMap(input)

        return trees.filter { !isOnTheEdge(input, it.key) }
            .maxOf { (t, u) ->
                val current = input[t.second][t.first].digitToInt()

                var score = 1;
                u.forEach {
                    var localStore = it.indexOfFirst { n -> n >= current }
                    score *= if (localStore == -1) it.size else localStore + 1
                }

                score
            }
    }
}
