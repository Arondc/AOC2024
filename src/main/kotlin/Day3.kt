package main.kotlin

class Day3 {
    private val input = readLinesFromFile("day3_star1.txt")

    fun starOne() {
        val mulRegEx = "mul\\((\\d+),(\\d+)\\)".toRegex()
        val doRegEx = "do\\(\\)".toRegex()
        val dontRegEx = "don't\\(\\)".toRegex()

        var activated = true
        val result =
        input.sumOf { line ->
            val multiplications =
                mulRegEx.findAll(line).map {
                    it.range.first to it.groups[1]!!.value.toLong() * it.groups[2]!!.value.toLong()
                }.toMap()
            val dos = doRegEx.findAll(line).map { it.range.first }.toList()
            val donts = dontRegEx.findAll(line).map { it.range.first }.toList()
            val indices = (dos + donts + multiplications.map { it.key }).sorted()
            var sum = 0L

            for (i in indices) {
                when {
                    dos.contains(i) -> activated = true
                    donts.contains(i) -> activated = false
                    activated -> sum += multiplications[i]!!
                }
            }
            sum
        }
        println(result)
    }
}

fun main() {
    Day3().starOne()
}