import Utility.readAutomaton

fun main(args: Array<String>) {
    readAutomaton(args[0]).toDFA().print()
}