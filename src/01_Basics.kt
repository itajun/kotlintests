import pkg1.DataClass1 // 1 class imported
import pkg1.sayHello // A function defined in the same file as the class. There is no correspondence between the file name and the class(es)/functions in the file
import java.io.Closeable
import java.io.FileWriter
import java.nio.file.Files

/**
 * This is public static void main(String... args)
 * This too: `<code>fun main(val args: Array<String>)</code>`. And BTW, `{@code x}` doesn't exist anymore. Use Markdown.
 */
fun main() {
    // BASICS
    println("Hi: ${max(-1, null)}") // prints Hi: null (intentionally stupid to make you think ;) )

    val a = 1; // ; optional, implicit type
    val b: Int = 2
    var c = 3;

    // `a = 2` would be illegal. val is final
    c = 4;

    c.coerceIn(1..2) // ranges `a..b` and extended functions. No idea of primitive/wrapper

    val simpleInstance = SimpleClass("test", "tset") // No `new`

    println("This is simpleInstance: $simpleInstance") // No need for `${}` if it is just the var
    println("This is name ${simpleInstance.name}, this is the full name ${simpleInstance.fullName}") // No need for get/set in properties. Only if calling from Java

    simpleInstance.surname = "newSurname" // This will implicitly call the setter

    println("This is the new full name ${simpleInstance.fullName}") // Will print test newSurname, proving that the property changed and the getter is using the new value (not evaluated upon construction)

    // CLASSES

    // Imported from a package. 1 class in the file, just like java
    val dataClass1 = DataClass1(1)

    println("dataClass1: $dataClass1")
    println("sayHello ${sayHello("test")}") // " inside expressions and using a function defined in the same file as a class

    // LOOPS
    for (person in PeopleFromCountries.values()) { // No need for val
        println("Person: $person: ${person.personName}/${person.nameInCaps()}")
    }

    // A `simple` map iteration
    val map = mapOf(1 to "a", 2 to "b", 3 to "c") // This receives a `varargs` Pair, equivalent to Java's `...`

    for ((key, value) in map) { // de-structuring, like javascript... but not :)...
        println("$key: $value")
    }

    println("map at key 1 is: ${map[1]}") // prints a


    // CONDITIONS
    val coutry = PeopleFromCountries.AUSTRALIA
    val domainEnding = when (coutry) { // receives the result of when
        PeopleFromCountries.AUSTRALIA -> "au"
        PeopleFromCountries.BRAZIL -> "br"
        else -> throw Exception("What????") // Just to illustrate. Idea shows it would never be reached
    }

    // `when` can also be used without argument, just use a boolean expression for each condition

    println("domainEnding: $domainEnding")

    println("simpleClass is SimpleInterface? ${simpleInstance is SimpleInterface}")

    // EXCEPTIONS

    // just like java, but `try` is an expression
    val myInt = try {
        "x".toInt()
    } catch (e: NumberFormatException) {
        0
    }
    println("myInt: $myInt")

    // REGEX AND STRINGS
    println("""
        this
        is a
        multiline
        string
    """) // we can use .trimIndent() to remove the indentation from here

    val matchEntire = """(.*)/(.*)\.(.*)""" // no need for \\. when using triple quotes
        .toRegex()
        .matchEntire("/temp/myfile.txt")

    if (matchEntire != null) {
        val (folder, file, ext) = matchEntire?.destructured // Cool
        println("fileName: $file")
    }

    // APPLY - Use and return self

    StringBuilder().apply {
        append("a")
        append("b")
    }.toString()

    // USE (alike try-with-resources)

//    FileWriter(Files.createTempFile("xpto", "tmp").toFile()).use {
//        it.write("something")
//    }

    /*
    This will close `Closeable` classes, since Kotlin targets jdk6. For auto-closeable, just add the dependency
    <dependency>
    <groupId>org.jetbrains.kotlin</groupId>
    <artifactId>kotlin-stdlib-jdk8</artifactId>
    </dependency>
     */

    class TestCloseable: Closeable {
        override fun close() {
            println("I'm closing")
        }
    }

    TestCloseable().use {
        println("Using closeable")
    }

    // SAFE CALL AND ELVIS

    val strNull: String? = null
    val strA: String? = "a"
    println(strNull?.toUpperCase()) // prints null
    println(strA?.toUpperCase()) // prints A
    println((strNull?:"b").toUpperCase()) // prints B

    /*
    Other interesting ones are
    `as?` casted if instanceof (or `is`) and `null` otherwise
    `!!` throws NPE if null
     */

    strNull?.let {  // Called only if non-null
        print("This will never be printed")
    }

    /*
    For Unit Tests, we may need to use `lateinit`, but I won't go into details here
     */

    /*
    Kotlin's `Object` is `Any`
    Kotlin's `void/Void` is `Unit`
    Kotlin has a `Nothing` type, for functions that never return (i.e. throw exception)
     */
}

/**
 * This is possible and really useful for `@Test`s
 */
fun `really descriptive name`() = "x"

/**
 * Guess what? This is a simple interface.
 */
interface SimpleInterface {
    fun simpleMethod(): String
}

/**
 * Simple class. This is not a data class, so no `toString()` by default.
 * @constructor creates a new instance of the simple class.
 * @property name will have getter (no setter since it is not `var`)
 */
class SimpleClass(val name: String, var surname: String): SimpleInterface { // no need to say extends/implements
    override fun simpleMethod(): String { // Override is required
        return "howdy!" // You open braces, you need return
    }

    val fullName: String // This is a `virtual` property with a getter
        get() = "$name $surname"
}

/**
 * This is also a function amd the result type is calculated by the compiler (Int?).
 * @param a is a non-null param.
 * @param b is also a nullable param.
 * @return the sum
 */
fun max(a: Int, b: Int?) = // No need for parenthesis or `return` if it is a one-liner.
    // Since b can be null, the elvis `?:` operator can be used to default it.
    if (a > b ?: 0)  a else b // if/else is an expression, so no ternary operator `a ? b : c`

/**
 * Enums are defined just like java, but no need for getters
 */
enum class PeopleFromCountries(val personName: String) {
    AUSTRALIA("john"), BRAZIL("joao"); // ; required here

    fun nameInCaps() = personName.toUpperCase() // Can define new methods
}