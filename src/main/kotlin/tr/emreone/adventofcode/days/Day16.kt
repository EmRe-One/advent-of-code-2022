package tr.emreone.adventofcode.days

object Day16 {

    // Regex to match comma seperated words
    val VALVE_PATTERN = """^Valve (?<name>\w+) has flow rate=(?<flowRate>\d+); tunnel(s)? lead(s)? to valve(s)? (?<adj>[\w\s,]+)$""".toRegex()
    val TOTAL_MINUTES = 30

    class TunnelSystem {
        lateinit var valves: Map<String, Valve>

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

    data class Valve(val name: String, val flowRate: Int, val adjTunnels: List<String>)

    fun part1(input: List<String>): Int {
        val tunnelSystem = TunnelSystem.parseFrom(input)


        return 0
    }

    fun part2(input: List<String>): Int {

        return 0
    }
}
