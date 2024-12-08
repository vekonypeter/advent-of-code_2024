package vp.aoc.day8

import vp.aoc.Cords
import vp.aoc.findCordsOf
import vp.aoc.isOnMap
import vp.aoc.makePairs
import java.io.File
import kotlin.math.abs
import kotlin.math.min

private val MAP = File("src/main/resources/inputs/day8.txt").readLines()
private val WIDTH = MAP.first().length
private val HEIGHT = MAP.size

fun main() {
    val frequencies = MAP.asSequence()
        .map { it.toSet() }.flatten().toSet().filter { it != '.' }
        .map { Frequency(it, MAP.findCordsOf(it)) }
        .toList()

    val res1 = frequencies.flatMap { it.findVisibleAnodes() }.toSet().size
    println("PART 1: $res1")

    val res2 = frequencies.flatMap { it.findVisibleAnodes2() }.toSet().size
    println("PART 2: $res2")
}

private data class Frequency(
    private val char: Char,
    private val cords: List<Cords>
) {
    private val pairs = cords.makePairs()

    fun findVisibleAnodes(): Set<Cords> = pairs
        .map { (a, b) ->
            val xDiff = a.x - b.x
            val yDiff = a.y - b.y
            listOf(Cords(a.x + xDiff, a.y + yDiff), Cords(b.x - xDiff, b.y - yDiff))
        }
        .flatten()
        .filter { it.isOnMap(MAP) }
        .toSet()

    fun findVisibleAnodes2(): Set<Cords> = pairs
        .map { (a, b) ->
            val xDiff = abs(a.x - b.x)
            val yDiff = abs(a.y - b.y)

            val right = if (a.x > b.x) a else b
            val left = if (a == right) b else a
            val up = if (a.y < b.y) a else b
            val down = if (a == up) b else a

            val spaceToRight = WIDTH - right.x - 1
            val spaceToLeft = left.x
            val spaceToUp = up.y
            val spaceToDown = HEIGHT - down.y - 1

            // left is upper
            if (left == up) {
                val countOnRight = min(spaceToRight / xDiff, spaceToDown / yDiff)
                val countOnLeft = min(spaceToLeft / xDiff, spaceToUp / yDiff)

                (1..countOnRight).map { i ->
                    Cords(right.x + i * xDiff, right.y + i * yDiff)
                } +
                        (1..countOnLeft).map { i ->
                            Cords(left.x - i * xDiff, left.y - i * yDiff)
                        }


            }
            // right is upper
            else {
                val countOnRight = min(spaceToRight / xDiff, spaceToUp / yDiff)
                val countOnLeft = min(spaceToLeft / xDiff, spaceToDown / yDiff)

                (1..countOnRight).map { i ->
                    Cords(right.x + i * xDiff, right.y - i * yDiff)
                } +
                        (1..countOnLeft).map { i ->
                            Cords(left.x - i * xDiff, left.y + i * yDiff)
                        }
            }
        }
        .flatten()
        .let { it + cords }
        .toSet()
}
