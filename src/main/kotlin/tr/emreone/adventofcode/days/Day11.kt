package tr.emreone.adventofcode.days

fun List<Long>.product(): Long = this.reduce { acc, i -> acc * i }

infix fun Long.isDivisibleBy(divisor: Int): Boolean = this % divisor == 0L

// greatest common divisor
infix fun Long.gcd(other: Long): Long {
    var a = this
    var b = other
    while (b != 0L) {
        val temp = b
        b = a % b
        a = temp
    }
    return a
}

// least common multiple
infix fun Long.lcm(other: Long): Long = (this * other) / (this gcd other)

fun List<Long>.gcd(): Long = this.reduce(Long::gcd)
fun List<Long>.lcm(): Long = this.reduce(Long::lcm)

object Day11 {

    val OPERATION_PATTERN = """Operation: new = old (\+|\*) (\d+|old)""".toRegex()
    val DIVISIBLE_PATTERN = """Test: divisible by (\d+)""".toRegex()
    val THROW_TO_PATTERN = """throw to monkey (\d+)""".toRegex()

    class Monkey(
        val id: Int,
        val recipients: MutableList<Long>,
        val operation: (Long) -> Long,
        val divisor: Int,
        val ifTrue: Int,
        val ifFalse: Int
    ) {
        var numberOfInspects = 0L

        companion object {
            /**
             * Monkey <id>:
             *   Starting items: <arrayOfItems>
             *   Operation: new = <formula>
             *   Test: divisible by <divisor>
             *     If true: throw to monkey <id>
             *     If false: throw to monkey <id>
             */
            fun parseFromString(input: String): Monkey {
                val lines = input.split("\n")

                val id = """Monkey (\d+):""".toRegex()
                    .find(lines[0])!!
                    .destructured
                    .component1()
                    .toInt()
                val recipients = lines[1]
                    .split(":")[1]
                        .split(",")
                        .map {
                            it.trim().toLong()
                        }
                        .toMutableList()

                val match = OPERATION_PATTERN.find(lines[2])
                val (op, value) = match!!.destructured
                val operation: (Long) -> Long = when (op) {
                    "+"  -> { old -> old + (value.toLongOrNull() ?: old) }
                    "*"  -> { old -> old * (value.toLongOrNull() ?: old) }
                    else -> throw IllegalArgumentException("Unknown operation: $op")
                }

                val divisor = DIVISIBLE_PATTERN.find(lines[3])!!.destructured.component1().toInt()
                val ifTrue = THROW_TO_PATTERN.find(lines[4])!!.destructured.component1().toInt()
                val ifFalse = THROW_TO_PATTERN.find(lines[5])!!.destructured.component1().toInt()

                return Monkey(id, recipients, operation, divisor, ifTrue, ifFalse)
            }
        }

        fun inspectItemOnTop(modifyWorryLevel: Boolean = false, worryLevelModifier: Long = 1L): Pair<Int, Long> {
            val worryLevel = this.recipients.removeFirst()
            val operatedWorryLevel = if (!modifyWorryLevel) {
                this.operation(worryLevel) / 3
            }
            else {
                this.operation(worryLevel) % worryLevelModifier
            }
            val testResult = operatedWorryLevel isDivisibleBy this.divisor

            val id = if (testResult) this.ifTrue else this.ifFalse

            this.numberOfInspects++
            return id to operatedWorryLevel
        }

        override fun toString(): String {
            return "Monkey $id (${numberOfInspects.toString().padStart(5, )}): " + this.recipients.joinToString(", ")
        }
    }

    fun part1(input: String): Long {
        val monkeys = input.split("\n\n").map(Monkey::parseFromString)

        repeat(20) {
            monkeys.forEach {monkey ->
                while(monkey.recipients.isNotEmpty()) {
                    val (id, worryLevel) = monkey.inspectItemOnTop()
                    monkeys[id].recipients.add(worryLevel)
                }
            }
        }

        return monkeys.map(Monkey::numberOfInspects)
            .sortedByDescending { it }
            .take(2)
            .product()
    }

    fun part2(input: String): Long {
        val monkeys = input.split("\n\n").map(Monkey::parseFromString)

        val commonDivisor = monkeys.map { it.divisor.toLong() }.lcm()
        repeat(10_000) {
            monkeys.forEach {monkey ->
                while(monkey.recipients.isNotEmpty()) {
                    val (id, worryLevel) = monkey.inspectItemOnTop(true, commonDivisor)
                    monkeys[id].recipients.add(worryLevel)
                }
            }
        }

        return monkeys.map(Monkey::numberOfInspects)
            .sortedByDescending { it }
            .take(2)
            .product()
    }
}
