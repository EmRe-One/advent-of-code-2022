package tr.emreone.adventofcode.days

import java.util.*
import kotlin.collections.HashMap

object Day16 {

    // Regex to match comma seperated words
    val VALVE_PATTERN = """^Valve (?<name>\w+) has flow rate=(?<flowRate>\d+); tunnel(s)? lead(s)? to valve(s)? (?<adj>[\w\s,]+)$""".toRegex()

    data class State(
        var at: String,
        var remainingTime: Int,
        var elephantAt: String? = null,
        var remainingElTime: Int? = null,
        var openedValves: Set<String> = setOf(),
        var totalFlow: Int = 0,
    ) : Comparable<State> {
        override fun compareTo(other: State) = compareValuesBy(this, other) { -it.totalFlow }
    }

    data class Valve(val name: String, val flowRate: Int, val adjTunnels: List<String>)

    class TunnelSystem {
        lateinit var valves: Map<String, Valve>
        lateinit var nonZeroNeighborsMap: Map<String, Map<String, Int>>

        fun initTunnelsystem() {
            this.nonZeroNeighborsMap = this.valves.keys.associateWith {
                getNonZeroNeighbors(it)
            }
        }

        fun getNonZeroNeighbors(curr: String, dist: Int = 0, visited: Set<String> = setOf()): Map<String, Int> {
            val neighbours = HashMap<String, Int>()
            for (adj in valves[curr]!!.adjTunnels.filter { it !in visited }) {
                if (valves[adj]!!.flowRate != 0) {
                    neighbours[adj] = dist+1
                }
                for ((name, d) in getNonZeroNeighbors(adj, dist+1, visited + setOf(curr))) {
                    neighbours[name] = minOf(d, neighbours.getOrDefault(name, 1000))
                }
            }
            return neighbours
        }

        fun solve(initialState: State): Int {
            val queue = PriorityQueue<State>().also { it.add(initialState) }
            var best = 0
            val visited: MutableMap<List<String>, Int> = mutableMapOf()
            while (queue.isNotEmpty()) {
                var (at, remainingTime,
                    elephantAt, remainingElTime,
                    openedValves, totalFlow) = queue.remove()

                best = maxOf(best, totalFlow)

                val vis = (openedValves.toList() + listOf(at, elephantAt ?: "")).sorted()

                if (visited.getOrDefault(vis, -1) >= totalFlow)
                    continue

                visited[vis] = totalFlow

                if (remainingElTime != null && elephantAt != null && remainingTime < remainingElTime) {
                    remainingTime = remainingElTime.also {
                        remainingElTime = remainingTime
                    }
                    at = elephantAt.also {
                        elephantAt = at
                    }
                }
                for ((neighbor, dist) in nonZeroNeighborsMap[at]!!) {
                    val newTime = remainingTime - dist - 1
                    val newFlow = totalFlow + this.valves[neighbor]!!.flowRate * newTime
                    if (newTime >= 0 && neighbor !in openedValves)
                        queue.add(
                            State(neighbor, newTime,
                                elephantAt, remainingElTime,
                                openedValves + setOf(neighbor), newFlow)
                        )
                }
            }
            return best
        }

        companion object {
            fun parseFrom(input: List<String>): TunnelSystem {
                return TunnelSystem().apply {
                    valves = input
                        .map {line ->
                            val matches = VALVE_PATTERN.matchEntire(line)!!.groups as MatchNamedGroupCollection
                            matches.let {
                                Valve(
                                    it["name"]!!.value,
                                    it["flowRate"]!!.value.toInt(),
                                    it["adj"]!!.value.split(", ")
                                )
                            }
                        }
                        .associateBy { it.name }
                }


            }
        }
    }

    fun part1(input: List<String>): Int {
        val tunnelSystem = TunnelSystem.parseFrom(input)
        tunnelSystem.initTunnelsystem()

        return tunnelSystem.solve(State("AA", 30))
    }

    fun part2(input: List<String>): Int {
        val tunnelSystem = TunnelSystem.parseFrom(input)
        tunnelSystem.initTunnelsystem()

        return tunnelSystem.solve(State("AA", 26, "AA", 26))
    }
}
