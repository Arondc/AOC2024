package main.kotlin

/**
 * Reads a file and returns it as Strings
 */
fun readLinesFromFile(filename : String) : List<String> {
    return object {}.javaClass
        .getResourceAsStream("/$filename")
        ?.bufferedReader()
        ?.readLines()!!
    }

/**
 * Reads a file and maps each line according to the mapping function
 */
fun <T> readLinesFromFile(filename : String, mapping: (String) -> (T)) : List<T> {
    return readLinesFromFile(filename)
        .map { mapping(it)}
}

/**
 * Reads a file and maps the whole file according to the mapping function
 */
fun <T> readLinesFromFileSingular(filename : String, mapping: (List<String>) -> (T)) : T {
    return mapping.invoke(readLinesFromFile(filename))
}

//Blatantly stolen from https://stackoverflow.com/a/74791785
inline fun <K, V> Iterable<K>.associateWithNotNull(
    valueTransform: (K) -> V?,
): Map<K, V> = buildMap {
    for (key in this@associateWithNotNull) {
        val value = valueTransform(key) ?: continue
        this[key] = value
    }
}