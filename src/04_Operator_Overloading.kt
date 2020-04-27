fun main() {
    val john = Human("John")
    val mary = Human("Mary")
    println(mary + john)
    println((mary + john)[5])
}

/*
This is where things start to get weird... There are conventions for operator overloading, decomposition, etc...


Expression

Function name

a * b	times
a / b	div
a % b	mod
a + b	plus
a - b	minus

unary operators and += -= can also be overridden
so can [], in and ..
 */

data class Human(val name: String) {
    operator fun plus(other: Human) = Human("${other.name} and $name's kid") // `operator` indicates that we're overriding the operator
    operator fun get(pos: Int): Char = name[pos]
}
