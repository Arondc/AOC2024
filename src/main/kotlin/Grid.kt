package main.kotlin

class NeighbourhoodGrid(lines: List<String>) : Grid(lines) {
    fun getNeighbour(cell: GridCell, direction: Direction): GridCell? {
        return cells.singleOrNull {
            it.coordinate.row == cell.coordinate.row + direction.diffRow
                    && it.coordinate.column == cell.coordinate.column + direction.diffColumn
        }
    }

    fun getNeighbours(startingCell: GridCell, directions: Set<Direction>): List<GridCell> =
        directions.mapNotNull { direction -> getCellAt(startingCell.coordinate + direction)}

    fun getNeighboursWithDirections(startingCell: GridCell, directions: Set<Direction>): Map<Direction,GridCell> =
        directions.associateWithNotNull { direction -> getCellAt(startingCell.coordinate + direction)}

    fun getAllNeighbours(startingCell: GridCell): List<GridCell> =
        Direction.entries.mapNotNull { direction ->
            getCellAt(startingCell.coordinate + direction)
        }

    fun getAllNeighboursWithDirections(startingCell: GridCell): Map<Direction, GridCell> =
        Direction.entries.associateWithNotNull { direction -> getCellAt(startingCell.coordinate + direction) }

    fun getNeighboursWithValue(startingCell: GridCell, expectedValue: Char): List<GridCell> =
        getAllNeighbours(startingCell).filter { it.content == expectedValue }

    fun getNeighboursWithValueAndDirections(startingCell: GridCell, expectedValue: Char): Map<Direction, GridCell> =
        getAllNeighboursWithDirections(startingCell).filter { it.value.content == expectedValue }

}

open class Grid(lines : List<String>) {
    open val cells : List<GridCell> =
        lines.flatMapIndexed { rowNumber, line ->
            line.mapIndexed { columnNumber, char ->
                GridCell(
                    row = rowNumber,
                    column = columnNumber,
                    value = char
                )
            }
        }

    fun getCellsWithValue(expectedValue: Char) : Set<GridCell> {
        return cells.filter { it.content == expectedValue }.toSet()
    }

    fun getCellAt(coordinate: Coordinate): GridCell? =
        cells.singleOrNull { it.coordinate.row == coordinate.row && it.coordinate.column == coordinate.column }


    override fun toString(): String =
        cells.toString()
}

data class GridCell(
    val coordinate: Coordinate,
    val content: Char,
    val tags: MutableSet<String> = mutableSetOf()
) {
    constructor(row: Int, column: Int, value: Char, tags: MutableSet<String> = mutableSetOf()) :
            this(coordinate = Coordinate(row, column), content = value, tags = tags)

    override fun toString(): String {
        return "$coordinate:$content"
    }
}

data class Coordinate(
    val row: Int, val column: Int,
) {
    operator fun plus(direction: Direction): Coordinate =
        Coordinate(this.row + direction.diffRow, this.column + direction.diffColumn)

    override fun toString(): String {
        return "$row:$column"
    }
}

enum class Direction(val diffRow:Int, val diffColumn:Int) {
    NORTH_WEST(-1, -1),
    NORTH(-1, 0),
    NORTH_EAST(-1, 1),
    EAST(0, 1),
    SOUTH_EAST(1, 1),
    SOUTH(1, 0),
    SOUTH_WEST(1,-1),
    WEST(0, -1); //<-- the one actual use of ; in Kotlin

    fun invert() =
        when(this) {
            NORTH_WEST -> SOUTH_EAST
            NORTH->SOUTH
            NORTH_EAST->SOUTH_WEST
            EAST->WEST
            SOUTH_EAST -> NORTH_WEST
            SOUTH->NORTH
            SOUTH_WEST->NORTH_EAST
            WEST->EAST
        }
}