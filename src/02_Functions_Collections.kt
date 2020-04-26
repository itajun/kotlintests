fun main() {
    val set = setOf("a", "b", "a")
    val list = listOf("a", "b", "a")

    println("List: $list, Set: $set")
    println("List type: ${list.javaClass}, Set type: ${set.javaClass}") // Java collections, nothing new
    println("joinToString ${joinToString(list, separator = " / ")}") // Named param (separator)
    list.sayHello() // This is cute :)... Also the reason why the types are the same as Java but the library has so many more methods
    printall("a", "b", "c")

    // LAMBDAS
    val people = setOf(
        Person("Child", 5),
        Person("Teenager", 15),
        Person("Adult", 25),
        Person("Senior", 65)
    )

    println(people.maxBy { it.age }) // `it` refers to the only parameter. No need for () if the last argument is a function
    println(people.maxBy({ x -> x.age })) // this is equivalent

    val byAge = { x: Person -> x.age }
    println(people.maxBy(byAge)) // so is this

    var iLlChange = 1
    val byAgeChange = { x: Person-> {
        iLlChange = 10 // Unlike Java, we can change the var
        byAge // No need for return
    } }

    // SEQUENCES (same as Java Streams)

    val seq = generateSequence(0) { it + 1 }
    seq
        .takeWhile { it < 100 }
        .map { " val: $it, " }
        .forEach { print(it) }
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

data class Person(
    val name: String,
    val age: Int
)