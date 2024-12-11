package main.kotlin

class Day4 {
    var grid =
        readLinesFromFileSingular("day4_star1.txt") {
            NeighbourhoodGrid(it)
        }

    fun starOne() {
        val result =
            grid.getCellsWithValue('X')
                .asSequence()
                .map { cell ->
                    grid.getNeighboursWithValueAndDirections(
                        startingCell = cell,
                        expectedValue = 'M'
                    )
                }
                .mapNotNull { list -> searchInDirectionForNext('A', list)}
                .mapNotNull { list -> searchInDirectionForNext('S', list)}
                .flatMap { it.entries }
                .count()
        println(result)
    }

    fun starTwo() {
        val result =
            grid.getCellsWithValue('A')
                .map { cell ->
                    grid.getNeighboursWithDirections(
                        cell,
                        setOf(Direction.NORTH_EAST, Direction.NORTH_WEST, Direction.SOUTH_EAST, Direction.SOUTH_WEST)
                    )
                }.filter {
                    //Only keep those with 2xM and 2xS
                        neighbours -> neighbours.count { (_,cell) -> cell.content == 'M' } == 2 &&
                        neighbours.count { (_,cell) -> cell.content == 'S' } == 2
                }.filter {
                    neighbours -> neighbours[Direction.NORTH_EAST]!!.content != neighbours[Direction.SOUTH_WEST]!!.content
                }.count()
        println(result)

    }

    private fun searchInDirectionForNext(next: Char, list: Map<Direction, GridCell>) =
        list.mapNotNull { (direction, cell) ->
            val value = grid.getNeighbour(cell, direction)
            when {
                value == null -> null
                value.content != next -> null
                else -> direction to value
            }
        }.toMap().ifEmpty { null }

}

fun main() {
    Day4().starOne()
    Day4().starTwo()
}