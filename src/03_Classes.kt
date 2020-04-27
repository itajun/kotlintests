fun main() {
    println("${IAmAChild() == IAmAChild()}") // This is TRUE in Kotlin, as it is converted to `.equals()`
    println("${IAmAChild() === IAmAChild()}") // This behaves like == in Java, so `false`
    IAmASingleton.resolveSomething(1)
    // `IAmASingleton()` is not possible

    IAmTheParent.thisReturnsOk() // Weird companion object. Check at the end, the method doesn't exist on the parent

    println(IHaveInfix("y") and "x")
}

class IAmFinal // Classes are final by default, unlike Java

abstract class IAmAbstract { // Abstract is open, obviously
    abstract fun x(): String
}

open class ICanBeExtended { // Make it open if you want to extend
    fun iAmFinal() = "final"
    open fun iAmNotFinal() = "not final"
}

interface IAmAnInterface
interface IAmAnotherInterface {
    val x: Int // this is a property that HAS to be implemented by implementers of this interface
}

class IAmAChild: ICanBeExtended(), IAmAnInterface, IAmAnotherInterface  { // This is also final and extends a class, implements 2 interfaces
    override val x = 1 // this is coming from IAmAnotherInterface
    val y = 2
    override fun iAmNotFinal(): String = "not final on child" // Override is always required, even for interface or abstract methods

    inner class IAmInner { // This will store a reference to the parent
        fun printParentProperty() {
            println(x)
        }
    }


    class IAmStatic { // This will NOT store a reference to the parent
        fun printParentProperty() {
            // `println(x)` not possible
        }
    }

    override fun equals(other: Any?): Boolean {
        if (other is IAmAChild) {
            return other.x == x // Smart cast, since the if already happened
        }
        return false;
    }
}

class Secretive private constructor() // A private constructor

class MoreThan1Constructor {
    constructor(a: Int) {
        // something
    }

    constructor(a: String) {
        // something
    }
}

/**
 * Now this is awesome! delegate directly to another class. We can also delegate to more than one, but conflicting
 * methods may have to be re-implemented.
 */
class DelegatingCollection<A>(
    innerList: Collection<A> = ArrayList()
) : Collection<A> by innerList {
    fun hasSingleItem() = size == 1 // This is not accessing the field, but the property via get ;)
}

/**
 * This is a singleton. You can't create new ones and methods are directly accessible
 *
 * Another very good use for objects is creating them inside other classes where you want a builder or a factory.
 * Make the object constructor private nd it'll force instances to be created by the enclosed object factory.
 *
 */
object IAmASingleton {
    // Something more interesting would be keeping a list of orders or something, but I'm lazy
    fun resolveSomething(a: Int) = a == 1
}

/**
 * One even weirder characteristic is the `companion object`. Basically the enclosed object is a singleton, but
 * its methods can be directly accessed by the parent.
 */
class IAmTheParent {
    companion object IAmACompanion {
        fun thisReturnsOk() = "Ok"
    }
}

data class IHaveInfix(val name: String) {
    infix fun and(x: String) = IHaveInfix("$x $name") // Infix don't need ()
}