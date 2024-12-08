package vp.aoc

import kotlin.time.Duration
import kotlin.time.TimeSource

internal data class Cords(val x: Int, val y: Int)

internal fun List<String>.findCordsOf(char: Char) = mapIndexed { y, line ->
    line.mapIndexedNotNull { x, c -> if (c == char) Cords(x, y) else null }
}.flatten()


internal fun <T> List<T>.makePairs(): List<Pair<T, T>> =
    mapIndexed { i, c -> subList(i + 1, size).map { c to it } }.flatten()

internal fun Cords.isOnMap(map: List<String>): Boolean = x in 0 until map.first().length && y in map.indices

internal inline fun <T> timed(block: () -> T): Pair<T, Duration> {
    val ts = TimeSource.Monotonic
    val start = ts.markNow()
    val result = block()
    val end = ts.markNow()
    return result to (end - start)
}
