package pkg1

/**
 * This is a data class. Getters, setters, hashcode and toString will be generated.
 */
data class DataClass1(val x: Int) // Notice that it doesn't match the file name

// Functions can also be defined here

fun sayHello(name: String) = "Hello $name"