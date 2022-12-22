package tr.emreone.adventofcode.days

import org.mariuszgromada.math.mxparser.Expression
import kotlin.math.roundToLong

object Day21 {

    val MONKEY_VALUE_PATTERN = """(\w+): (\d+)""".toRegex()
    val MONKEY_EQ_PATTERN = """(\w+): (\w+) ([*/+\-]) (\w+)""".toRegex()

    class RootCalculator() {
        val allMonkeys: MutableMap<String, Monkey> = mutableMapOf()

        fun calculateMonkeyWithId(id: String): Long {
            val monkey = allMonkeys[id]!!
            monkey.value?.let { return it }

            val left = calculateMonkeyWithId(monkey.waitFor!!.first)
            val right = calculateMonkeyWithId(monkey.waitFor.second)

            monkey.value = when (monkey.operation) {
                '+' -> left + right
                '-' -> left - right
                '*' -> left * right
                '/' -> left / right
                '=' -> if (left == right) 1L else 0L
                else -> throw UnsupportedOperationException()
            }
            return monkey.value!!
        }

        fun buildEquationForMonkeyWithId(y: String, x: String): String {
            val monkey = allMonkeys[y]!!

            monkey.value?.let { return "${monkey.value}" }

            val (left, right) = monkey.waitFor!!

            var leftEquation = if (left == x) "x" else buildEquationForMonkeyWithId(left, x)
            if (!leftEquation.contains("x")) {
                leftEquation = calculateMonkeyWithId(left).toString()
            }
            var rightEquation = if (right == x) "x" else buildEquationForMonkeyWithId(right, x)
            if (!rightEquation.contains("x")) {
                rightEquation = calculateMonkeyWithId(right).toString()
            }

            return "($leftEquation ${monkey.operation!!} $rightEquation)"
        }

        companion object {
            fun parse(input: List<String>): RootCalculator {
                return RootCalculator().apply {
                    input.map { line ->
                        val monkey = Monkey.parse(line)
                        allMonkeys[monkey.id] = monkey
                    }
                }
            }
        }
    }

    class Monkey(val id: String, var value: Long?, var operation: Char?, val waitFor: Pair<String, String>?) {

        companion object {
            fun parse(input: String): Monkey {
                return if (MONKEY_VALUE_PATTERN.matches(input)) {
                    val (id, value) = MONKEY_VALUE_PATTERN.find(input)!!.destructured
                    Monkey(id, value.toLong(), null, null)
                } else if (MONKEY_EQ_PATTERN.matches(input)) {
                    val (id, left, operation, right) = MONKEY_EQ_PATTERN.find(input)!!.destructured
                    Monkey(id, null, operation[0], Pair(left, right))
                } else {
                    throw IllegalArgumentException("Invalid input: $input")
                }
            }
        }
    }

    fun part1(input: List<String>): Long {
        val calculator = RootCalculator.parse(input)

        return calculator.calculateMonkeyWithId("root")
    }

    fun part2(input: List<String>): Long {
        val calculator = RootCalculator.parse(input)

        calculator.allMonkeys["root"]!!.operation = '='
        val (left, right) = calculator.allMonkeys["root"]!!.waitFor!!

        val leftEq = calculator.buildEquationForMonkeyWithId(left, "humn")
        val rightEq = calculator.buildEquationForMonkeyWithId(right, "humn")

        val e = Expression("solve ( ($leftEq - $rightEq), x, 0, ${Long.MAX_VALUE})")
        return e.calculate().roundToLong()
    }
}
