package vp.aoc.day7

import vp.aoc.timed
import java.io.File

fun main() {
    val equations = File("src/main/resources/inputs/day7.txt")
        .readLines()
        .map { it.split(": ", " ").map(String::toLong) }
        .map { Equation(it.first().toLong(), it.subList(1, it.size)) }

    val (res1, time1) = timed {
        equations
            .filter { it.canBeCorrect1() }
            .sumOf { it.result }
    }
    println("PART 1: $res1 - $time1")

    val (res2, time2) = timed {
        equations
            .filter { it.canBeCorrect2() }
            .sumOf { it.result }
    }
    println("PART 2: $res2 - $time2")
}

private data class Equation(
    val result: Long,
    val terms: List<Long>,
) {
    fun canBeCorrect1(i: Int = 1, currentSum: Long = terms.first()): Boolean {
        if (i > terms.indices.last) return currentSum == result
        if (currentSum > result) return false // optimization - makes no sense to go further

        val sumRes = canBeCorrect1(i + 1, currentSum + terms[i])
        val mulRes = canBeCorrect1(i + 1, currentSum * terms[i])

        return sumRes || mulRes
    }

    fun canBeCorrect2(i: Int = 1, currentSum: Long = terms.first()): Boolean {
        if (i > terms.indices.last) return currentSum == result
        if (currentSum > result) return false // optimization - makes no sense to go further

        val sumRes = canBeCorrect2(i + 1, currentSum + terms[i])
        val mulRes = canBeCorrect2(i + 1, currentSum * terms[i])
        val conRes = canBeCorrect2(i + 1, "$currentSum${terms[i]}".toLong())

        return sumRes || mulRes || conRes
    }
}
