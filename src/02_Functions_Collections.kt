fun main() {
    val set = setOf("a", "b", "a")
    val list = listOf("a", "b", "a")

    println("List: $list, Set: $set")
    println("List type: ${list.javaClass}, Set type: ${set.javaClass}") // Java collections, nothing new
    println("joinToString ${joinToString(list, separator = " / ")}") // Named param (separator)
    list.sayHello() // This is cute :)... Also the reason why the types are the same as Java but the library has so many more methods
    printall("a", "b", "c")
}

fun <T> joinToString( // Define generic type
    collection: Collection<T>,
    separator: String = ",", // Default values
    prefix: String = "{",
    suffix: String = "}"
): String {
    with (StringBuilder()) {// With is back! :)
        append(prefix)
        for ((idx, e) in collection.withIndex()) { // We have the index with the element
            append(if (idx > 0) "$separator$e" else e)
        }
        append(suffix)
        return toString()
    }
}

fun <T> Collection<T>.sayHello() = println("Hello!") // Add a method to an existing type. We can add properties as well, but they of course can only access public members of the extended class

fun printall(vararg values: String) = println("values type,value: ${values.javaClass}, ${values.asList()}") // Varargs