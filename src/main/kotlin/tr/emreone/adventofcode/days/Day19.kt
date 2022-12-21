package tr.emreone.adventofcode.days

import tr.emreone.utils.Logger.logger

object Day19 {

    val PATTERN_BLUEPRINT_ID = """Blueprint (\d+):""".toRegex()
    val PATTERN_ORE_ROBOT = """Each ore robot costs (\d+) ore.""".toRegex()
    val PATTERN_CLAY_ROBOT = """Each clay robot costs (\d+) ore.""".toRegex()
    val PATTERN_OBSIDIAN_ROBOT = """Each obsidian robot costs (\d+) ore and (\d+) clay.""".toRegex()
    val PATTERN_GEODE_ROBOT = """Each geode robot costs (\d+) ore and (\d+) obsidian.""".toRegex()

    enum class MATERIAL {
        ORE, CLAY, OBSIDIAN, GEODE
    }

    class Robot(val material: MATERIAL) {
        var oreCost = 0
        var clayCost = 0
        var obsidianCost = 0
    }

    class Blueprint(val id: Int) {
        val robotPriceTable = mutableMapOf<MATERIAL, Robot>()
        var maxProduction = 0

        fun maxGeodeProduction(
            collectedMaterials: MutableMap<MATERIAL, Int> = MATERIAL.values().associateWith { 0 }.toMutableMap(),
            robots: MutableMap<MATERIAL, Int> = MATERIAL.values().associateWith { if (it == MATERIAL.ORE) 1 else 0 }.toMutableMap(),
            timeLeft: Int
        ): Int {

            if (timeLeft == 0) {
                if (collectedMaterials[MATERIAL.GEODE]!! > maxProduction) {
                    maxProduction = collectedMaterials[MATERIAL.GEODE]!!
                    logger.info { "New max production: $maxProduction" }
                }
                return collectedMaterials[MATERIAL.GEODE]!!
            }

            // if we can not produce in best-case enough geode to beat the current max, then return
            var geodeRessource = collectedMaterials[MATERIAL.GEODE]!!
            var geodeRobots = robots[MATERIAL.GEODE]!!
            (timeLeft downTo 0).forEach {
                geodeRessource += geodeRobots
                geodeRobots += 1
            }
            if (geodeRessource < maxProduction) {
                return 0
            }

            var bestProduction = 0

            // check if we can build robot and compare production with and without building robot
            MATERIAL.values().reversed().forEach {
                val robot = robotPriceTable[it]!!

                if (canBuild(robot, collectedMaterials)) {
                    collectedMaterials[MATERIAL.ORE] = collectedMaterials[MATERIAL.ORE]!! - robot.oreCost
                    collectedMaterials[MATERIAL.CLAY] = collectedMaterials[MATERIAL.CLAY]!! - robot.clayCost
                    collectedMaterials[MATERIAL.OBSIDIAN] = collectedMaterials[MATERIAL.OBSIDIAN]!! - robot.obsidianCost
                    robots.forEach { (type, count) ->
                        collectedMaterials[type] = collectedMaterials[type]!! + count
                    }
                    robots[it] = robots[it]!! + 1

                    val production = maxGeodeProduction(collectedMaterials.toMutableMap(), robots.toMutableMap(), timeLeft - 1)

                    if (production > bestProduction) {
                        bestProduction = production
                    }

                    // revert changes
                    robots[it] = robots[it]!! - 1
                    robots.forEach { (type, count) ->
                        collectedMaterials[type] = collectedMaterials[type]!! - count
                    }
                    collectedMaterials[MATERIAL.ORE] = collectedMaterials[MATERIAL.ORE]!! + robot.oreCost
                    collectedMaterials[MATERIAL.CLAY] = collectedMaterials[MATERIAL.CLAY]!! + robot.clayCost
                    collectedMaterials[MATERIAL.OBSIDIAN] = collectedMaterials[MATERIAL.OBSIDIAN]!! + robot.obsidianCost
                }
            }

            val production = maxGeodeProduction(collectedMaterials.toMutableMap(), robots.toMutableMap(), timeLeft - 1)

            if (production > bestProduction) {
                bestProduction = production
            }

            return bestProduction
        }

        private fun printPortmonnaie(collectedMaterials: Map<MATERIAL, Int>) {
            collectedMaterials.forEach{ (type, count) ->
                logger.info { "\tCollected $count $type" }
            }
        }

        companion object {
            fun parse(line: String): Blueprint {
                val blueprintId = PATTERN_BLUEPRINT_ID.find(line)!!.destructured.component1().toInt()
                val blueprint = Blueprint(blueprintId)
                // Ore Robot
                blueprint.robotPriceTable[MATERIAL.ORE] = Robot(MATERIAL.ORE).also {
                    it.oreCost = PATTERN_ORE_ROBOT.find(line)!!.destructured.component1().toInt()
                }
                // Clay Robot
                blueprint.robotPriceTable[MATERIAL.CLAY] = Robot(MATERIAL.CLAY).also {
                    it.oreCost = PATTERN_CLAY_ROBOT.find(line)!!.destructured.component1().toInt()
                }
                blueprint.robotPriceTable[MATERIAL.OBSIDIAN] = Robot(MATERIAL.OBSIDIAN).also {
                    it.oreCost = PATTERN_OBSIDIAN_ROBOT.find(line)!!.destructured.component1().toInt()
                    it.clayCost = PATTERN_OBSIDIAN_ROBOT.find(line)!!.destructured.component2().toInt()
                }
                blueprint.robotPriceTable[MATERIAL.GEODE] = Robot(MATERIAL.GEODE).also {
                    it.oreCost = PATTERN_GEODE_ROBOT.find(line)!!.destructured.component1().toInt()
                    it.obsidianCost = PATTERN_GEODE_ROBOT.find(line)!!.destructured.component2().toInt()
                }
                return blueprint
            }

            private fun canBuild(robot: Robot, collectedMaterials: Map<MATERIAL, Int>): Boolean {
                return collectedMaterials[MATERIAL.ORE]!! >= robot.oreCost
                        && collectedMaterials[MATERIAL.CLAY]!! >= robot.clayCost
                        && collectedMaterials[MATERIAL.OBSIDIAN]!! >= robot.obsidianCost
            }
        }
    }

    fun part1(input: List<String>, minutes: Int): Int {
        val blueprints = input.map { Blueprint.parse(it) }

        val scores = blueprints.map { blueprint ->
            blueprint.id to blueprint.maxGeodeProduction(timeLeft = minutes)
        }

        return scores.sumOf {
            logger.info { "Blueprint ${it.first} produces ${it.second} geodes" }
            it.first * it.second
        }
    }

    fun part2(input: List<String>): Int {

        return 0
    }
}
