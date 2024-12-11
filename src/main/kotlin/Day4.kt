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
                .mapNotNull { neighbours -> searchInDirectionForNext('A', neighbours)}
                .mapNotNull { neighbours -> searchInDirectionForNext('S', neighbours)}
                .flatMap { it.entries }
                .count()
        println(result)
    }

    fun starTwo() {
        val result =
            //Starting point is the middle of each X-MAS
            grid.getCellsWithValue('A')
                .map { cell ->
                    grid.getNeighboursWithDirections(
                        cell,
                        setOf(Direction.NORTH_EAST, Direction.NORTH_WEST, Direction.SOUTH_EAST, Direction.SOUTH_WEST)
                    )
                }.filter {
                    //Only keep those with 2xM and 2xS
                        neighbours ->
                    neighbours.count { (_, cell) -> cell.content == 'M' } == 2 &&
                            neighbours.count { (_, cell) -> cell.content == 'S' } == 2
                }.count {
                    //If one pair is matching the other one is automatically also matching
                        neighbours ->
                    neighbours[Direction.NORTH_EAST]!!.content != neighbours[Direction.SOUTH_WEST]!!.content
                }
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