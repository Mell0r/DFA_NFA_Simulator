import java.io.File
import java.util.*
import kotlin.math.pow

object Utility {
    class Automate(
        val n: Int,
        val m: Int,
        val startNodes: List<Int>
    ) {
        val edges: List<MutableList<Pair<Int, Int>>> = List(n) { mutableListOf() }
        val isFinish = MutableList(n) { false }

        fun dfs (
            v: Int,
            input: List<Int>,
            ind: Int = 0
        ): Boolean {
            if (ind == input.size)
                return isFinish[v]
            edges[v].forEach { (symb, to) ->
                if (symb == input[ind]) {
                    if (dfs(to, input, ind + 1))
                        return true
                }
            }
            return false
        }

        fun simulate(automateInput: List<Int>): Boolean {
            startNodes.forEach {
                if (dfs(it, automateInput))
                    return true
            }
            return false
        }

        fun toDFA(): Automate {
            fun List<Int>.toNode() =
                this.fold(0) { sum, cur -> sum + (1 shl cur) }

            val dfa = Automate(
                2.0.pow(n).toInt(),
                m,
                listOf(startNodes.toNode())
            )
            for (mask in 1 until dfa.n) {
                for (ch in 0 until m) {
                    val edgeToSet = edges.filterIndexed{ index, _ -> (mask shr index) % 2 == 1 }
                        .flatMap { list -> list.filter { it.first == ch }.map { it.second } }
                        .toSet().toList()
                    if (edgeToSet.isNotEmpty())
                        dfa.edges[mask].add(Pair(ch, edgeToSet.toNode()))
                }
                for (v in 0 until n)
                    dfa.isFinish[mask] = dfa.isFinish[mask] or (((mask shr v) % 2 == 1) and isFinish[v])
            }
            return dfa
        }

        fun print() {
            println(n)
            println(m)
            startNodes.forEach { print("$it ") }
            println()
            isFinish.forEachIndexed { ind, finish -> if(finish) print("$ind ") }
            println()
            edges.forEachIndexed { from, e -> e.forEach { (ch, to) -> println("$from $ch $to") } }
        }
    }

    fun getIntListFromScanner(scanner: Scanner): List<Int> {
        return scanner.nextLine().split(' ').map{ it.toInt() }
    }

    fun readAutomate(automatePath: String): Automate {
        val automateScanner = Scanner(File(automatePath))
        val automate = Automate(
            automateScanner.nextLine().toInt(),
            automateScanner.nextLine().toInt(),
            getIntListFromScanner(automateScanner),
        )
        getIntListFromScanner(automateScanner).forEach { automate.isFinish[it] = true }
        while (automateScanner.hasNextLine()) {
            val (from, symb, to) = getIntListFromScanner(automateScanner)
            automate.edges[from].add(Pair(symb, to))
        }
        return automate
    }
}