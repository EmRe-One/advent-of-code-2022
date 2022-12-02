package tr.emreone.adventofcode.days

import tr.emreone.utils.Logger.logger

object Day02 {

    /* 1 for Rock          A  X
     * 2 for Paper, and    B  Y
     * 3 for Scissors      C  Z
     *
     * plus
     *
     * 0 if you lost
     * 3 if the round was a draw, and
     * 6 if you won
     */
    fun part1(input: List<String>): Int {
        val rounds = input.map { round ->
            val (a,b) = round.split(" ")
            Pair(a.toCharArray().first() - 'A', b.toCharArray().first() - 'X')
        }

        var totalScore = 0;
        rounds.forEach { round ->
            if ((round.first + 1) % 3  == round.second) {
                totalScore += 6
            }
            else if (round.second == round.first) {
                totalScore += 3
            }
            totalScore += (round.second + 1)
        }
        return totalScore
    }

    /* 1 for Rock          A  X -> needs to lose
     * 2 for Paper, and    B  Y -> needs a draw
     * 3 for Scissors      C  Z -> needs a win
     *
     * plus
     *
     * 0 if you lost
     * 3 if the round was a draw, and
     * 6 if you won
     */
    fun part2(input: List<String>): Int {
        val rounds = input.map { round ->
            val (a,b) = round.split(" ")
            Pair(a.toCharArray().first() - 'A', b.toCharArray().first() - 'X')
        }

        var totalScore = 0;
        rounds.forEach { round ->
            val currentScore = when (round.second) {
                0 -> {
                    // needs to lose
                    // -1 for losing symbol and +3 to avoid negativ values
                    ((round.first - 1 + 3) % 3 + 1)
                }
                1 -> {
                    // needs a draw
                    3 + (round.first + 1)
                }
                2 -> {
                    6 + ((round.first + 1) % 3 + 1)
                }
                else -> {
                    logger.error { "Invalid round: $round" }
                    0
                }
            }
            totalScore += currentScore
        }

        return totalScore
    }
}
