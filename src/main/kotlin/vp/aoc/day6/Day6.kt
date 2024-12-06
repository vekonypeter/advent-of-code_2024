package vp.aoc.day6

import vp.aoc.Cords
import vp.aoc.day6.Day6.Direction.*
import java.io.File

fun main() {
    val day = Day6(File("src/main/resources/inputs/day6.txt"))

    println("PART 1: ${day.calcRoute().size}")
    println("PART 2: ${day.calcObstacles().size}")
}

private class Day6(file: File) {

    private val map = file.readLines()
    private val width = map.first().length
    private val height = map.size

    private val startPos = map
        .mapIndexedNotNull { y, line -> line.indexOf("^").takeIf { it != -1 }?.let { Cords(it, y) } }.first()

    fun calcRoute(): Set<Cords> {
        var pos: Cords = startPos
        var dir = NORTH
        val visited = mutableSetOf(pos)
        while (true) {
            val nextPos = pos.next(dir)
            if (!nextPos.isOnMap()) break

            if (map[nextPos.y][nextPos.x] == '#') {
                dir = dir.next()
            } else {
                pos = nextPos
                visited.add(pos)
            }
        }

        return visited
    }

    fun calcObstacles(): List<Cords> = (calcRoute() - startPos)
        .filter { routeCord ->
            val modifiedMap = map.mapIndexed { y, line ->
                if (y == routeCord.y) line.replaceRange(routeCord.x, routeCord.x + 1, "#") else line
            }

            var pos: Cords = startPos
            var dir = NORTH
            val visited = mutableSetOf(pos to dir)
            while (true) {
                val nextPos = pos.next(dir)
                if (!nextPos.isOnMap()) break

                if (modifiedMap[nextPos.y][nextPos.x] == '#') {
                    dir = dir.next()
                } else {
                    pos = nextPos
                }

                val newVisited = pos to dir
                if (visited.contains(newVisited)) return@filter true
                visited.add(newVisited)
            }
            return@filter false
        }

    private fun Cords.next(dir: Direction): Cords = when (dir) {
        NORTH -> Cords(x, y - 1)
        EAST -> Cords(x + 1, y)
        SOUTH -> Cords(x, y + 1)
        WEST -> Cords(x - 1, y)
    }

    private fun Cords.isOnMap(): Boolean = x in 0 until width && y in 0 until height

    private enum class Direction {
        NORTH, EAST, SOUTH, WEST;

        fun next(): Direction = Direction.entries[(ordinal + 1) % entries.size]
    }
}
