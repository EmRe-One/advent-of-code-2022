package tr.emreone.adventofcode.days

object Day02 {

    /*
    * Score is calculated by adding the score of shape with the score for the round
    *
    * 1 for Rock
    * 2 for Paper, and
    * 3 for Scissors
    *
    * plus
    *
    * 0 if you lost
    * 3 if the round was a draw, and
    * 6 if you won
    */
    enum class HandShape(
        val score: Int
    ) {
        ROCK(1),
        PAPER(2),
        SCISSORS(3);

        fun getShapeForWin(): HandShape {
            return when (this) {
                ROCK -> PAPER
                PAPER -> SCISSORS
                SCISSORS -> ROCK
            }
        }

        fun getShapeForLoose(): HandShape {
            return when (this) {
                ROCK -> SCISSORS
                PAPER -> ROCK
                SCISSORS -> PAPER
            }
        }

        companion object {
            fun parseFrom(char: Char): HandShape {
                return when (char) {
                    'A', 'X' -> ROCK
                    'B', 'Y' -> PAPER
                    'C', 'Z' -> SCISSORS
                    else -> throw IllegalArgumentException("Shape must be A, B, C or X, Y, Z")
                }
            }
        }
    }

    private fun parseRounds(input: List<String>) = input.map { round ->
        val (player1, player2) = round.split(" ").map {
            HandShape.parseFrom(it.first())
        }
        player1 to player2
    }


    fun part1(input: List<String>): Int {
        val rounds = parseRounds(input)

        return rounds.sumOf { round ->
            // return score of your shape plus score of the round
            round.second.score + when (round.second) {
                round.first.getShapeForWin() -> 6
                round.first                  -> 3
                else                         -> 0
            }
        }
    }

    fun part2(input: List<String>): Int {
        val rounds = parseRounds(input)
        return rounds.sumOf { round ->
            when (round.second) {
                HandShape.ROCK     -> 0 + round.first.getShapeForLoose().score  // needs a lose
                HandShape.PAPER    -> 3 + round.first.score                     // needs a draw
                HandShape.SCISSORS -> 6 + round.first.getShapeForWin().score    // needs a win
            }
        }
    }
}
