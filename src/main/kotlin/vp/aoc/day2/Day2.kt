package vp.aoc.day2

import java.io.File
import kotlin.math.abs

fun main() {
    val reports = File("src/main/resources/inputs/day2.txt")
        .readLines()
        .map { it.split(" ").map(String::toInt) }

    val (safeReports, nonSafeReports) = reports.partition { it.isSafe() }

    val res1 = safeReports.size
    println("PART 1: $res1")

    val res2 = res1 + nonSafeReports.count { it.canBeSafe() }
    println("PART 2: $res2")
}

private fun List<Int>.isSafe(): Boolean {
    val firstDiff = this[1] - this[0]
    if (firstDiff == 0) return false
    val increasing = firstDiff > 0

    for (i in 1 until size) {
        val diff = this[i] - this[i - 1]

        // if direction changes or diff is not between 1 and 3
        if (increasing && diff < 0 || !increasing && diff > 0 || abs(diff) !in 1..3) {
            return false
        }
    }
    return true
}

private fun List<Int>.canBeSafe(): Boolean =
    this.indices.any { i -> this.filterIndexed { c, _ -> c != i }.isSafe() }