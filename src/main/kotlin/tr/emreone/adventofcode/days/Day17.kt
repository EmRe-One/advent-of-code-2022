package tr.emreone.adventofcode.days

import kotlin.math.max
import kotlin.math.min

object Day17 {
    val CHAMBER_WIDTH = 7
    val OFFSET_X = 2
    val OFFSET_Y = 3

    class Shape(var shape: String) {
        var width = shape.lines().first().length
        var height = shape.lines().count()
    }

    val SHAPES = arrayOf(
        Shape("####"),
        Shape(" # \n###\n # "),
        Shape("###\n  #\n  #"),
        Shape("#\n#\n#\n#"),
        Shape("##\n##")
    )

    fun part1(input: String, numberOfRocks: Int = 2022): Int {
        var currentMovement = -1
        val numberOfMovements = input.length
        var currentShapeIndex = -1
        var chamber = mutableListOf<String>("=".repeat(CHAMBER_WIDTH))

        fun printChamber() {
            println("Chamber:")
            chamber.reversed().forEach { println("|$it|") }
            println()
        }

        fun moveShape(x: Int, y: Int, direction: Char, shape: Shape): Boolean {
            when (direction) {
                '>' -> {
                    shape.shape.lines().forEachIndexed { s_y, line ->
                        line.forEachIndexed { s_x, c ->
                            if (c == '#') {
                                if ((x + s_x + 1) >= CHAMBER_WIDTH
                                    || (y + shape.height - s_y in chamber.indices
                                            && chamber[y + shape.height - s_y][x + s_x + 1] != ' ')
                                ) {
                                    return false
                                }
                            }
                        }
                    }
                }

                '<' -> {
                    shape.shape.lines().forEachIndexed { s_y, line ->
                        line.forEachIndexed { s_x, c ->
                            if (c == '#') {
                                if ((x + s_x - 1) < 0
                                    || (y + shape.height - s_y in chamber.indices
                                            && chamber[y + shape.height - s_y][x + s_x - 1] != ' ')
                                ) {
                                    return false
                                }
                            }
                        }
                    }
                }

                'v' -> {
                    shape.shape.lines().forEachIndexed { s_y, line ->
                        line.forEachIndexed { s_x, c ->
                            if (c == '#') {
                                // Reach bottom
                                if (y + shape.height - s_y - 1 < 0) {
                                    return false
                                }
                                if (y + shape.height - s_y - 1 in chamber.indices
                                    && chamber[y + shape.height - s_y - 1][x + s_x] != ' '
                                ) {
                                    return false
                                }
                            }
                        }
                    }
                }
            }
            return true
        }

        repeat(numberOfRocks) {
            currentShapeIndex = (currentShapeIndex + 1) % SHAPES.size
            println("Shape: ${currentShapeIndex + 1}")

            val currentShape = SHAPES[currentShapeIndex]
            var shapeX = OFFSET_X
            var shapeY = chamber.size + OFFSET_Y

            while (true) {
                // next movement
                currentMovement = (currentMovement + 1) % numberOfMovements
                println("Movement: ${input[currentMovement]}")
                when (input[currentMovement]) {
                    '<' -> {
                        if (moveShape(shapeX, shapeY, input[currentMovement], currentShape)) {
                            shapeX--
                            println("Moved left to $shapeX, $shapeY")
                        }
                    }

                    '>' -> {
                        if (moveShape(shapeX, shapeY, input[currentMovement], currentShape)) {
                            shapeX++
                            println("Moved right to $shapeX, $shapeY")
                        }
                    }
                }

                if (!moveShape(shapeX, shapeY, 'v', currentShape)) {
                    while (chamber.size < shapeY + currentShape.height) {
                        chamber.add(" ".repeat(CHAMBER_WIDTH))
                    }
                    currentShape.shape.lines().forEachIndexed { s_y, line ->
                        line.forEachIndexed { s_x, c ->
                            if (c == '#') {
                                chamber[shapeY + s_y] = chamber[shapeY + s_y]
                                    .replaceRange(shapeX + s_x, shapeX + s_x + 1, "#")
                            }
                        }
                    }
                    break
                } else {
                    shapeY--
                    println("Moved down to $shapeX, $shapeY")
                }
            }
            printChamber()
        }
        return chamber.size
    }

    fun part2(input: String): Int {

        return 0
    }
}
