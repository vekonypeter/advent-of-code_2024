package vp.aoc.day1

import java.io.File
import kotlin.math.abs

private val LINE_REGEX = Regex("""(\d+)\s+(\d+)""")

fun main() {
    val raw = File("src/main/resources/inputs/day1.txt")
        .readLines()
        .map { checkNotNull(LINE_REGEX.find(it)?.groupValues) { "line $it does not match" } }

    val first = raw.map { it[1].toInt() }.sorted()
    val second = raw.map { it[2].toInt() }.sorted()

    val res1 = first.foldIndexed(0) { i, acc, v -> acc + abs(v - second[i]) }
    println("PART 1: $res1")

    val res2 = first.foldIndexed(0) { i, acc, v -> v * second.count { it == v } + acc }
    println("PART 1: $res2")
}
