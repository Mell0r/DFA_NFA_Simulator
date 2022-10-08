import Utility.getIntListFromScanner
import Utility.readAutomaton
import java.io.File
import java.util.*

fun main(args: Array<String>) {
    val automate = readAutomaton(args[0])
    print(
        if (automate.simulate(getIntListFromScanner(Scanner(File(args[1])))))
            "accept"
        else
            "reject"
    )
}