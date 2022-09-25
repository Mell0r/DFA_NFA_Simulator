import Utility.readAutomate

fun main(args: Array<String>) {
    readAutomate(args[0]).toDFA().print()
}