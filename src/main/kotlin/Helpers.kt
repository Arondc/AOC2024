package main.kotlin

fun readLinesFromFile(filename : String) : List<String> {
    return object {}.javaClass
        .getResourceAsStream("/$filename")
        ?.bufferedReader()
        ?.readLines()!!
    }

fun <T> readLinesFromFile(filename : String, mapping: (String) -> (T)) : List<T> {
    return readLinesFromFile(filename)
        .map { mapping(it)}
}