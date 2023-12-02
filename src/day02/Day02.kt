package day02

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf { solvePart1(it) }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf { solvePart2(it) }
    }

    // test if implementation meets criteria from the description, like:
    check(part1(readInput("Day02_test")) == 8)
    check(part2(readInput("Day02_p2_test")) == 2286)

    val input = readInput("Day02")
    part1(input).also { println("Day02_P1_Solution: $it") }
    part2(input).also { println("Day02_P2_Solution: $it") }
}

val bag = mutableMapOf(
        "red" to 12,
        "green" to 13,
        "blue" to 14
)

fun solvePart1(line: String): Int {
    val (gameID, draws) = extractGameIdAndDraws(line)
    val validDraws = mutableListOf<Boolean>()
    draws.forEach { draw ->
        val colors = draw.split(",")
        colors.forEach {
            val bucket = it.trim().split(Regex("\\s"))
            val number = Integer.parseInt(bucket[0])
            val color = bucket[1]
            validDraws.add(bag[color]!! >= number)
        }
    }
    if (!validDraws.contains(false))
        return Integer.parseInt(gameID)
    return 0
}

fun solvePart2(line: String): Int {
    val (gameID, draws) = extractGameIdAndDraws(line)
    val minimumNumberOfColoredCubes = mutableMapOf(
            "red" to 0,
            "green" to 0,
            "blue" to 0
    )
    draws.forEach { draw ->
        val colors = draw.split(",")
        colors.forEach {
            val bucket = it.trim().split(Regex("\\s"))
            val number = Integer.parseInt(bucket[0])
            val color = bucket[1]
            addMinimumNumberOfCubes(minimumNumberOfColoredCubes, color, number)

        }
    }
    println("Game $gameID could have been played with a minimum of :${minimumNumberOfColoredCubes}")

    var cubePower = 1
    minimumNumberOfColoredCubes.values.stream().mapToInt { it }.forEach {cubePower *=it}
    return cubePower
}

private fun extractGameIdAndDraws(line: String): Pair<String, List<String>> {
    val splitLine = line.split(":")
    val gameID = splitLine[0].split(Regex("[A-Za-z]+"))[1].trim()
    val draws = splitLine[1].split(";")
    return Pair(gameID, draws)
}

private fun addMinimumNumberOfCubes(minimumNumberOfColoredCubes: MutableMap<String, Int>, color: String, number: Int) {
    if (minimumNumberOfColoredCubes[color] != null && minimumNumberOfColoredCubes[color]!! <= number) {
        minimumNumberOfColoredCubes[color] = number
    }
}
