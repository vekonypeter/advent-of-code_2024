package vp.aoc.day4

import java.io.File
import kotlin.properties.Delegates.notNull

var WIDTH by notNull<Int>()
var HEIGHT by notNull<Int>()

fun main() {
    val text = File("src/main/resources/inputs/day4.txt")
        .readLines()
    WIDTH = text[0].length
    HEIGHT = text.size

    val xCords = text.findCordsOf('X')
    val res1 = xCords.map { it.getPaths() }.flatten()
        .count { text.isPathEqualTo(it, "XMAS") }
    println("PART 1: $res1")

    val aCords = text.findCordsOf('A')
    val res2 = aCords.mapNotNull { it.getPaths2() }
        .count { text.isPathEqualTo(it.first, "MAS") && text.isPathEqualTo(it.second, "MAS") }
    println("PART 2: $res2")
}

private fun List<String>.findCordsOf(char: Char) = mapIndexed { y, line ->
    line.mapIndexedNotNull { x, c -> if (c == char) Cords(x, y) else null }
}.flatten()

private fun String.isEqualOrReverse(other: String) = this == other || this == other.reversed()

private fun List<String>.isPathEqualTo(path: List<Cords>, str: String) = atCords(path).isEqualOrReverse(str)

private fun List<String>.atCords(cords: Cords): Char = this[cords.y][cords.x]

private fun List<String>.atCords(cords: List<Cords>): String =
    cords.joinToString("") { "${atCords(it)}" }

private data class Cords(val x: Int, val y: Int) {

    fun getPaths() = listOf(
        // north
        listOf(this, Cords(x, y - 1), Cords(x, y - 2), Cords(x, y - 3)),
        // north-east
        listOf(this, Cords(x + 1, y - 1), Cords(x + 2, y - 2), Cords(x + 3, y - 3)),
        // east
        listOf(this, Cords(x + 1, y), Cords(x + 2, y), Cords(x + 3, y)),
        // south-east
        listOf(this, Cords(x + 1, y + 1), Cords(x + 2, y + 2), Cords(x + 3, y + 3)),
        // south
        listOf(this, Cords(x, y + 1), Cords(x, y + 2), Cords(x, y + 3)),
        // south-west
        listOf(this, Cords(x - 1, y + 1), Cords(x - 2, y + 2), Cords(x - 3, y + 3)),
        // west
        listOf(this, Cords(x - 1, y), Cords(x - 2, y), Cords(x - 3, y)),
        // north-west
        listOf(this, Cords(x - 1, y - 1), Cords(x - 2, y - 2), Cords(x - 3, y - 3)),
    ).filter { it.isAllOnMap() }

    fun getPaths2() = (
            listOf(Cords(x - 1, y - 1), this, Cords(x + 1, y + 1)) to
                    listOf(Cords(x + 1, y - 1), this, Cords(x - 1, y + 1))
            )
        .takeIf { it.first.isAllOnMap() && it.second.isAllOnMap() }

    private fun List<Cords>.isAllOnMap(): Boolean = all { it.isOnMap() }

    private fun isOnMap(): Boolean = x in 0 until WIDTH && y in 0 until HEIGHT
}
