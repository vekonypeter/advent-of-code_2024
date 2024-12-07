package vp.aoc

import kotlin.time.Duration
import kotlin.time.TimeSource

internal data class Cords(val x: Int, val y: Int)

inline fun <T> timed(block: () -> T): Pair<T, Duration> {
    val ts = TimeSource.Monotonic
    val start = ts.markNow()
    val result = block()
    val end = ts.markNow()
    return result to (end - start)
}
