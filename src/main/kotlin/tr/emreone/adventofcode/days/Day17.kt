package tr.emreone.adventofcode.days

import tr.emreone.kotlin_utils.Logger.logger

object Day17 {
    private val CHAMBER_WIDTH = 7
    private val START_OFFSET_X = 2
    private val START_OFFSET_Y = 3

    class Shape(var shape: String) {
        var width = shape.lines().first().length
        var height = shape.lines().count()
    }

    /*
             ║     ║   ║
     ════   ═╬═    ║   ║   ╔╗
             ║   ══╝   ║   ╚╝
                       ║
     */
    private val SHAPES = arrayOf(
        Shape("----"),
        Shape(" | \n-+-\n | "),
        Shape("  |\n  |\n--J"),
        Shape("|\n|\n|\n|"),
        Shape("**\n**")
    )

    class Chamber() {
        var currentShapeIndex = -1
        var placedShapes = mutableListOf<String>()

        fun nextShape(): Shape {
            currentShapeIndex = (currentShapeIndex + 1) % SHAPES.size
            return SHAPES[currentShapeIndex]
        }

        fun moveShapeLeft(x: Int, y: Int, shape: Shape): Pair<Int, Int> {
            shape.shape.lines().reversed().forEachIndexed { s_y, line ->
                line.forEachIndexed { s_x, c ->
                    if (c != ' ') {
                        if ((x + s_x - 1) < 0
                            || (y + s_y in placedShapes.indices
                                    && placedShapes[y + s_y][x + s_x - 1] != ' ')
                        ) {
                            return x to y
                        }
                    }
                }
            }

            return (x - 1) to y
        }

        fun moveShapeRight(x: Int, y: Int, shape: Shape): Pair<Int, Int> {
            shape.shape.lines().reversed().forEachIndexed { s_y, line ->
                line.forEachIndexed { s_x, c ->
                    if (c != ' ') {
                        if ((x + s_x + 1) >= CHAMBER_WIDTH
                            || (y + s_y in placedShapes.indices && placedShapes[y + s_y][x + s_x + 1] != ' ')
                        ) {
                            return x to y
                        }
                    }
                }
            }

            return (x + 1) to y
        }

        fun moveShapeDown(x: Int, y: Int, shape: Shape): Pair<Int, Int> {
            shape.shape.reversed().lines().forEachIndexed { s_y, line ->
                line.forEachIndexed { s_x, c ->
                    if (c != ' ') {
                        val checkY = y + s_y - 1
                        if (checkY < 0 || (checkY in placedShapes.indices && placedShapes[checkY][x + s_x] != ' ')) {
                            return x to y
                        }
                    }
                }
            }

            return x to (y - 1)
        }

        fun placeShapeAt(x: Int, y: Int, shape: Shape) {
            while (placedShapes.size < y + shape.height) {
                placedShapes.add(" ".repeat(CHAMBER_WIDTH))
            }
            shape.shape.lines().reversed().forEachIndexed { s_y, line ->
                line.forEachIndexed { s_x, c ->
                    if (c != ' ') {
                        placedShapes[y + s_y] = placedShapes[y + s_y]
                            .replaceRange(x + s_x, x + s_x + 1, c.toString())
                    }
                }
            }
        }

        fun printChamber() {
            placedShapes.reversed().forEach { logger.info { "|$it|" } }
            logger.info { "+" + "-".repeat(CHAMBER_WIDTH) + "+" }
        }
    }

    fun part1(input: String, numberOfRocks: Int = 2022): Int {
        val chamber = Chamber()
        var currentMovementIndex = -1
        val numberOfMovements = input.length

        repeat(numberOfRocks) {
            val currentShape = chamber.nextShape()

            var shapeX = START_OFFSET_X
            var shapeY = chamber.placedShapes.size + START_OFFSET_Y

            while (true) {
                // next movement
                currentMovementIndex = (currentMovementIndex + 1) % numberOfMovements
                val nextMovement = input[currentMovementIndex]

                when (nextMovement) {
                    '<' -> {
                        val (newX, _) = chamber.moveShapeLeft(shapeX, shapeY, currentShape)
                        shapeX = newX
                        logger.debug { "Move left to $shapeX, $shapeY" }
                    }

                    '>' -> {
                        val (newX, _) = chamber.moveShapeRight(shapeX, shapeY, currentShape)
                        shapeX = newX
                        logger.debug { "Move right to $shapeX, $shapeY" }
                    }
                }

                val (_, newY) = chamber.moveShapeDown(shapeX, shapeY, currentShape)
                if (newY == shapeY) {
                    chamber.placeShapeAt(shapeX, shapeY, currentShape)
                    break
                } else {
                    shapeY = newY
                    logger.debug { "Move down to $shapeX, $shapeY" }
                }
            }
        }

        // chamber.printChamber()
        return chamber.placedShapes.size
    }

    fun part2(input: String, numberOfRocks: Long = 1_000_000_000_000L): Long {
        val chamber = Chamber()
        var currentMovementIndex = -1
        val numberOfMovements = input.length

        return -1L
    }
}
