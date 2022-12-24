package tr.emreone.adventofcode.days

import java.util.*
import kotlin.collections.ArrayDeque

object Day19 {

    val PATTERN_BLUEPRINT_ID = """Blueprint (\d+):""".toRegex()
    val PATTERN_ORE_ROBOT = """Each ore robot costs (\d+) ore.""".toRegex()
    val PATTERN_CLAY_ROBOT = """Each clay robot costs (\d+) ore.""".toRegex()
    val PATTERN_OBSIDIAN_ROBOT = """Each obsidian robot costs (\d+) ore and (\d+) clay.""".toRegex()
    val PATTERN_GEODE_ROBOT = """Each geode robot costs (\d+) ore and (\d+) obsidian.""".toRegex()

    enum class MATERIAL(val index: Int) {
        ORE(0),
        CLAY(1),
        OBSIDIAN(2),
        GEODE(3)
    }

    class Robot() {
        var oreCost = 0
        var clayCost = 0
        var obsidianCost = 0
    }

    data class State(
        var materials: MutableList<Int> = mutableListOf(0, 0, 0, 0),
        var robots: MutableList<Int> = mutableListOf(1, 0, 0, 0),
        var timeLeft: Int
    ): Comparable<State> {
        override fun compareTo(other: State): Int {
            return compareValuesBy(this, other) { it.score() }
        }

        fun tick(): State {
            this.robots.forEachIndexed { index, count ->
                this.materials[index] += count
            }
            this.timeLeft--
            return this
        }

        private fun score(): Int {
            return this.robots.sum()
        }

        fun isBetterThan(other: State): Boolean {
            return this.materials.zip(other.materials).all { (a, b) -> a >= b }
                    && this.robots.zip(other.robots).all { (a, b) -> a >= b }
        }

        fun copy(): State {
            return State(this.materials.toMutableList(), this.robots.toMutableList(), this.timeLeft)
        }
    }


    class Blueprint(val id: Int) {
        val robotPriceTable = mutableListOf<Robot>(Robot(), Robot(), Robot(), Robot())
        var maxProduction = 0

        fun maxGeodeProduction(initialTimeLeft: Int): Int {
            val queue = ArrayDeque<State>()
            queue.add(State(timeLeft = initialTimeLeft))

            val bestRobots = PriorityQueue<State>()
            val visited: MutableSet<State> = mutableSetOf()

            fun addState(state: State) {
                if (state in visited)
                    return
                visited.add(state)
                for (robot in bestRobots) {
                    if (robot.isBetterThan(state))
                        return
                }
                bestRobots.add(state)
                if (bestRobots.size > 1000){
                    bestRobots.poll()
                }

                queue.add(state)
            }

            while (queue.isNotEmpty()) {
                val state = queue.removeFirst()
                val stateAfterOneMinute = state.copy().tick()

                // if time is over, remember the best geodeProduction
                if (stateAfterOneMinute.timeLeft == 0) {
                    if (stateAfterOneMinute.materials[MATERIAL.GEODE.index] > this.maxProduction) {
                        this.maxProduction = stateAfterOneMinute.materials[MATERIAL.GEODE.index]
                    }
                    continue
                }

                // if we can produce a geode-robot, do it
                if (this.canBuildForState(state, MATERIAL.GEODE)) {
                    val newState = stateAfterOneMinute.copy().also {
                        it.materials[MATERIAL.ORE.index] -= this.robotPriceTable[MATERIAL.GEODE.index].oreCost
                        it.materials[MATERIAL.OBSIDIAN.index] -= this.robotPriceTable[MATERIAL.GEODE.index].obsidianCost
                        it.robots[MATERIAL.GEODE.index] += 1
                    }
                    addState(newState)
                }
                // if we can produce an obsidian-robot, do it
                else if (this.canBuildForState(state, MATERIAL.OBSIDIAN)) {

                    val newState = stateAfterOneMinute.copy().also {
                        it.materials[MATERIAL.ORE.index] -= this.robotPriceTable[MATERIAL.OBSIDIAN.index].oreCost
                        it.materials[MATERIAL.CLAY.index] -= this.robotPriceTable[MATERIAL.OBSIDIAN.index].clayCost
                        it.robots[MATERIAL.OBSIDIAN.index] += 1
                    }
                    addState(newState)
                }
                // if we can produce a clay, do it
                else {
                    if (this.canBuildForState(state, MATERIAL.CLAY)) {
                        val newState = stateAfterOneMinute.copy().also {
                            it.materials[MATERIAL.ORE.index] -= this.robotPriceTable[MATERIAL.CLAY.index].oreCost
                            it.robots[MATERIAL.CLAY.index] += 1
                        }
                        addState(newState)
                    }
                    // if we can produce an ore, do it
                    if (this.canBuildForState(state, MATERIAL.ORE)) {
                        val newState = stateAfterOneMinute.copy().also {
                            it.materials[MATERIAL.ORE.index] -= this.robotPriceTable[MATERIAL.ORE.index].oreCost
                            it.robots[MATERIAL.ORE.index] += 1
                        }
                        addState(newState)
                    }
                }

                // the case if we can produce nothing :D
                addState(stateAfterOneMinute.copy())
            }

            return this.maxProduction
        }

        private fun canBuildForState(state: State, robotForMaterial: MATERIAL): Boolean {
            val robot = this.robotPriceTable[robotForMaterial.index]

            return state.materials[MATERIAL.ORE.index] >= robot.oreCost
                    && state.materials[MATERIAL.CLAY.index] >= robot.clayCost
                    && state.materials[MATERIAL.OBSIDIAN.index] >= robot.obsidianCost
        }

        companion object {
            fun parse(line: String): Blueprint {
                val blueprintId = PATTERN_BLUEPRINT_ID.find(line)!!.destructured.component1().toInt()
                val blueprint = Blueprint(blueprintId)
                // Ore Robot
                blueprint.robotPriceTable[MATERIAL.ORE.index].also {
                    it.oreCost = PATTERN_ORE_ROBOT.find(line)!!.destructured.component1().toInt()
                }
                // Clay Robot
                blueprint.robotPriceTable[MATERIAL.CLAY.index].also {
                    it.oreCost = PATTERN_CLAY_ROBOT.find(line)!!.destructured.component1().toInt()
                }
                blueprint.robotPriceTable[MATERIAL.OBSIDIAN.index].also {
                    it.oreCost = PATTERN_OBSIDIAN_ROBOT.find(line)!!.destructured.component1().toInt()
                    it.clayCost = PATTERN_OBSIDIAN_ROBOT.find(line)!!.destructured.component2().toInt()
                }
                blueprint.robotPriceTable[MATERIAL.GEODE.index].also {
                    it.oreCost = PATTERN_GEODE_ROBOT.find(line)!!.destructured.component1().toInt()
                    it.obsidianCost = PATTERN_GEODE_ROBOT.find(line)!!.destructured.component2().toInt()
                }
                return blueprint
            }
        }
    }

    fun part1(input: List<String>, minutes: Int): Int {
        val blueprints = input.map { Blueprint.parse(it) }

        val scores = blueprints.map { blueprint ->
            blueprint.id to blueprint.maxGeodeProduction(minutes)
        }

        return scores.sumOf {
            // logger.info { "Blueprint ${it.first} produces ${it.second} geodes" }
            it.first * it.second
        }
    }

    fun part2(input: List<String>, minutes: Int, noOfElephants: Int, startIndex: Int = 0): Int {
        val blueprints = input.map { Blueprint.parse(it) }
            .drop(startIndex)
            .slice(0 until noOfElephants)

        val scores = blueprints.map { it.maxGeodeProduction(minutes) }


        return scores.reduce { a, b -> a * b }
    }
}
