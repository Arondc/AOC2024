package main.kotlin

fun main() {
    Day5().starOne()
    Day5().starTwo()
}

class Day5 {
    private val lines = readLinesFromFile("day5_star1.txt")
    private val ruleLines = lines.takeWhile { it.isNotEmpty() }.toSet()
    private val updates = (lines - ruleLines).filter { it.isNotEmpty() }
        .map { line -> line.split(",").map { it.toInt() } }.toSet()
    private val rules =
        ruleLines.map { it.split("|") }.map { it.first().toInt() to it.last().toInt() }.toSet()

    private val successors = rules.groupBy({ it.first }, { it.second })
    private val predecessors = rules.groupBy({ it.second }, { it.first })

    fun starOne() {
        println("starOne: ${calculateMiddlePageSum(getValidUpdates())}")
    }

    fun starTwo() {
        val invalidUpdates = updates - getValidUpdates()
        println("starTwo: ${calculateMiddlePageSum(invalidUpdates)}")
    }

    private fun calculateMiddlePageSum(invalidUpdates: Set<List<Int>>) =
        reorderUpdates(invalidUpdates).sumOf { update -> update[update.size / 2] }

    private fun getValidUpdates() = updates.filter { update ->
        update.zipWithNext().all {
            successors[it.first]!!.contains(it.second)
        }
    }.toSet()

    private fun reorderUpdates(invalidUpdates: Set<List<Int>>) =
        invalidUpdates.map { update ->
            update.map { page -> page to (predecessors[page]!!.filter { update.contains(it) }) }
                .sortedBy { it.second.size }
                .map { it.first }
        }.toSet()
}




