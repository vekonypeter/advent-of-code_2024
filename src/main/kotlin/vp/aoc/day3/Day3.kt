package vp.aoc.day3

import java.io.File

fun main() {
    val lines = File("src/main/resources/inputs/day3.txt")
        .readLines()

    val regex1 = Regex("""mul\((\d+),(\d+)\)""")
    val res1 = lines
        .asSequence()
        .map { regex1.findAll(it).toList() }
        .flatten()
        .sumOf { it.groupValues[1].toInt() * it.groupValues[2].toInt() }

    println("PART 1: $res1")

    val regex2 = Regex("""(don't\(\))|(do\(\))|(mul\((\d+),(\d+)\))""")
    var enabled = true
    val res2 = lines
        .asSequence()
        .map { regex2.findAll(it).toList() }
        .flatten()
        .map { it.groupValues }
        .sumOf {
            when {
                it[0].startsWith("mul") -> if (enabled) it[4].toInt() * it[5].toInt() else 0

                it[0].startsWith("don't") -> {
                    enabled = false
                    0
                }

                it[0].startsWith("do") -> {
                    enabled = true
                    0
                }

                else -> error("Unexpected instruction!")
            }
        }

    println("PART 2: $res2")
}
