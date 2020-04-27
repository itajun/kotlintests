fun createTable() =
    table {
        tr {
            td { "x" }
        }
    }

fun main() {
    println(createTable())
}

open class Tag(val tag: String) {
    private val children = mutableListOf<Tag>()
    protected fun <T: Tag> doInit(child: T, init: T.() -> Unit) {
        child.init()
        children.add(child)
    }
    override fun toString(): String = "<$tag>${children.joinToString("")}<$tag>"
}

fun table(init: TABLE.() -> Unit) = TABLE().apply(init) // using apply to return this. if we just called init, the result would be Unit

// `TR.() -> Unit` read like: To the class type TR, add a method that takes no params and returns nothing. That is just going to evaluate the code {}, since the child is created and added by `doInit()`

class TABLE: Tag("table") {
    fun tr(init: TR.() -> Unit) = doInit(TR(), init)
}
class TR: Tag("tr") {
    fun td(init: TD.() -> Unit) = doInit(TD(), init)
}
class TD: Tag("td")