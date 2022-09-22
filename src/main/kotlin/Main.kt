import java.io.File
import java.util.*

fun getIntListFromScanner(scanner: Scanner): List<Int> {
    return scanner.nextLine().split(' ').map{ it.toInt() }
}

fun dfs (
    v: Int,
    g: List<MutableList<Pair<Int, Int>>>,
    isFinish: MutableList<Boolean>,
    sInp: List<Int>,
    ind: Int = 0
): Boolean {
    if (ind == sInp.size)
        return isFinish[v]
    g[v].forEach { (symb, to) ->
        if (symb == sInp[ind]) {
            if (dfs(to, g, isFinish, sInp, ind + 1))
                return true
        }
    }
    return false
}

fun simulateAutomate(automatePath: String, automateInput: List<Int>): Boolean {
    val automateScanner = Scanner(File(automatePath))
    val n = automateScanner.nextLine().toInt()
    val m = automateScanner.nextLine().toInt()
    val st = getIntListFromScanner(automateScanner)
    val g: List<MutableList<Pair<Int, Int>>> = List(n) { mutableListOf() }
    val isFinish = MutableList(n) { false }
    getIntListFromScanner(automateScanner).forEach { isFinish[it] = true }
    while (automateScanner.hasNextLine()) {
        val (from, symb, to) = getIntListFromScanner(automateScanner)
        g[from].add(Pair(symb, to))
    }
    st.forEach {
        if (dfs(it, g, isFinish, automateInput))
            return true
    }
    return false
}

fun main(args: Array<String>) {
    val automateInput = getIntListFromScanner(Scanner(File(args[1])))
    print(
        if (simulateAutomate(args[0], automateInput))
            "accept"
        else
            "reject"
    )
}