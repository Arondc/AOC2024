package main.kotlin

class Day2 {
    private val reports =
        readLinesFromFile("day2_star1.txt").map { report -> report.split(" ").map { it.toLong() } }.map { Report(it) }
            .toList()

    fun starOne() {
        println(reports.count { it.safe })
    }

    fun starTwo() {
        println(reports.count { it.safe || it.dampenedSafe })
    }


    class Report(private val levels: List<Long>) {
        val safe by lazy { isSafe(levels) }
        val dampenedSafe by lazy { dampen() }

        private fun dampen(): Boolean {
            //Is there more than one doublet
            val occurrences = levels.associateWith { value -> levels.count { it == value } }
            if (occurrences.count { (_, amount) -> amount > 1 } > 1) return false

            //Check if we can fix a sequence by removing a value
            return levels.indices.firstOrNull {
                val tempList = levels.filterIndexed { i, _ -> i != it }
                isSafe(tempList)
            } != null
        }

        private fun isSafe(levels: List<Long>) = isIncreasing(levels) || isDecreasing(levels)

        private fun isDecreasing(levels: List<Long>) =
            levels.asSequence().windowed(size = 2).all { (first, second) -> first > second && second - first >= -3 }

        private fun isIncreasing(levels: List<Long>) =
            levels.asSequence().windowed(size = 2).all { (first, second) -> first < second && second - first <= 3 }
    }
}

fun main() {
    Day2().starOne()
    Day2().starTwo()
}