package tr.emreone.adventofcode.days

object Day21 {

    val MONKEY_VALUE_PATTERN = """(\w+): (\d+)""".toRegex()
    val MONKEY_EQ_PATTERN = """(\w+): (\w+) (\*|\/|\+|\-) (\w+)""".toRegex()

    class RootCalculator() {
        val allMonkeys: MutableMap<String, Monkey> = mutableMapOf()

        val monkeysWithUnknownValues: MutableMap<String, Monkey> = mutableMapOf()
        val monkeysWithKnownValues: MutableMap<String, Monkey> = mutableMapOf()

        fun resolveEquations() {
            while(this.monkeysWithUnknownValues.isNotEmpty()) {
                val id = this.monkeysWithUnknownValues.firstNotNullOfOrNull { (id, monkey) ->
                    if (this.evaluateFor(id)) {
                        id
                    } else {
                        null
                    }
                }
                if (id != null) {
                    val monkey = this.monkeysWithUnknownValues.remove(id)
                    this.monkeysWithKnownValues[id] = monkey!!
                }
            }
        }

        fun evaluateFor(id: String): Boolean {
            val monkey = monkeysWithUnknownValues[id]!!
            val left = monkey.waitFor?.first?.let { monkeysWithKnownValues[it] }?.value
            val right = monkey.waitFor?.second?.let { monkeysWithKnownValues[it] }?.value

            if (left == null || right == null) {
                return false
            }

            monkey.value = when (monkey.operation) {
                '+' -> left + right
                '-' -> left - right
                '*' -> left * right
                '/' -> left / right
                '=' -> if (left == right) 1L else 0L
                else -> return false
            }
            return true
        }

        fun valueOf(id: String): Long? {
            return monkeysWithKnownValues[id]?.value
        }

        fun resetMonkeys() {
            monkeysWithUnknownValues.clear()
            monkeysWithKnownValues.clear()

            allMonkeys.forEach { (key, monkey) ->
                monkey.reset()
                if (monkey.value != null) {
                    monkeysWithKnownValues[key] = monkey
                } else {
                    monkeysWithUnknownValues[key] = monkey
                }
            }
        }

        companion object {
            fun parse(input: List<String>): RootCalculator {
                return RootCalculator().apply {
                    input.map { line ->
                        val monkey = Monkey.parse(line)
                        allMonkeys[monkey.id] = monkey

                        if (monkey.value != null) {
                            monkeysWithKnownValues[monkey.id] = monkey
                        }
                        else {
                            monkeysWithUnknownValues[monkey.id] = monkey
                        }
                    }
                }
            }
        }
    }

    class Monkey(val id: String, var value: Long?, var operation: Char?, val waitFor: Pair<String, String>?) {
        private val initialValue: Long? = value

        fun reset() {
            value = initialValue
        }

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

        calculator.resolveEquations()

        return calculator.valueOf("root")!!
    }

    fun part2(input: List<String>): Long {
        val calculator = RootCalculator.parse(input)

        calculator.monkeysWithUnknownValues["root"]!!.operation = '='
        var humanCounter = -1L

        while(true) {
            calculator.resetMonkeys()
            calculator.monkeysWithUnknownValues.remove("humn") // remove human counter if its existing in monkeys
            calculator.monkeysWithKnownValues["humn"]!!.value = ++humanCounter

            if (humanCounter % 1000 == 0L) {
                println("Trying $humanCounter")
            }

            calculator.resolveEquations()

            if (calculator.valueOf("root") == 1L) {
                break
            }
        }

        return humanCounter
    }
}
