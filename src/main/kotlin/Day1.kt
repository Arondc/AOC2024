package main.kotlin

import kotlin.math.abs

class Day1 {
    fun starOne() {
        val (listOne, listTwo) = readLists()
        val sumOfDistances = listOne.sorted().zip(listTwo.sorted()).sumOf { abs(it.first - it.second) }
        println("list distance: $sumOfDistances")
    }

    fun starTwo() {
        val (listOne, listTwo) = readLists()
        val sumOfSimilarities = listOne.sumOf { listOneElement ->
            listOneElement * listTwo.count { it == listOneElement }
        }
        println("similarity score: $sumOfSimilarities")
    }

    private fun readLists() = readLinesFromFile("day1_star1.txt") {
        val (valueListOne, valueListTwo) = it.split(" +".toRegex())
        valueListOne.toLong() to valueListTwo.toLong()
    }.unzip()
}

fun main() {
    Day1().starOne()
    Day1().starTwo()
}