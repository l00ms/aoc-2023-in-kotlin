fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf { solvePart1(it) }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf { solvePart2(it) }
    }

    // test if implementation meets criteria from the description, like:
    check(part1(readInput("Day01_test")) == 142)
    check(part2(readInput("Day01_p2_test")) == 281)

    val input = readInput("Day01")
    part1(input).also { println("Day01_P1_Solution: $it") }
    part2(input).also { println("Day01_P2_Solution: $it") }
}


fun solvePart1(line: String): Int {
    return extractCalibrationValue(line)
}

fun solvePart2(line: String): Int {
    return extractCalibrationValue(replaceStringDigits(line))
}


fun replaceStringDigits(line: String): String {
    return line.mapIndexedNotNull { index, c ->
        if (c.isDigit()) c
        else
            line.checkForWords(index).firstNotNullOfOrNull { stringToDigitMap[it] }
    }.joinToString()
}

private fun String.checkForWords(index: Int): List<String> {
    val min = stringToDigitMap.map { it.key.length }.min()
    val max = stringToDigitMap.map { it.key.length }.max()
    return (min..max).map { len: Int -> substring(index, (index + len).coerceAtMost(length)) }
}

