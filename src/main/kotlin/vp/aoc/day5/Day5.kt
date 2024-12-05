package vp.aoc.day5

import java.io.File

private typealias Rule = Pair<Int, Int>
private typealias Rules = List<Rule>

fun main() {
    val (rules, pages) = File("src/main/resources/inputs/day5.txt").readLines().run {
        val dividedAt = indexOf("")
        subList(0, dividedAt).map { it.split("|") }.map { it[0].toInt() to it[1].toInt() } to
                subList(dividedAt + 1, size).map { it.split(",").map(String::toInt) }
    }

    val (pagesInOrder, pagesNotInOrder) = pages.partition { it.isInOrder(rules) }

    val res1 = pagesInOrder.sumOf { it.getMiddle() }
    println("PART 1: $res1")

    val res2 = pagesNotInOrder.sumOf { it.computeFixed(rules).getMiddle() }
    println("PART 2: $res2")
}

private fun List<Int>.isInOrder(rules: Rules) = rules.all {
    val idx1 = indexOf(it.first)
    val idx2 = indexOf(it.second)

    idx1 == -1 || idx2 == -1 || idx1 < idx2
}

private fun List<Int>.getMiddle() = this[(size - 1) / 2]

private fun List<Int>.computeFixed(rules: Rules): List<Int> {
    val applicableRules = rules.findApplicableTo(this)
    return this.sortedByDescending { n -> applicableRules.count { n == it.first } }
}

private fun Rules.findApplicableTo(page: List<Int>) =
    filter { page.indexOf(it.first) > -1 && page.indexOf(it.second) > -1 }
