import java.io.File
import java.util.*
import kotlin.math.pow

object Utility {
    class Automaton(
        val stateQuantity: Int,
        val alphabetSize: Int,
        val startNodes: List<Int>,
        rawEdges: List<MutableList<Pair<Int, Int>>> = List(stateQuantity) { mutableListOf() },
        val isFinish: MutableList<Boolean> = MutableList(stateQuantity) { false }
    ) {
        val edges: List<MutableList<Pair<Int, Int>>> =
            rawEdges.map { list -> list.sortedBy { it.first }.toMutableList() }
        private fun dfsSimulator (
            pos: Int,
            input: List<Int>,
            ind: Int = 0
        ): Boolean {
            if (ind == input.size)
                return isFinish[pos]
            edges[pos].forEach { (symbol, to) ->
                if (symbol == input[ind]) {
                    if (dfsSimulator(to, input, ind + 1))
                        return true
                }
            }
            return false
        }

        private fun dfsPainter (
            pos: Int,
            used: MutableList<Boolean>
        ) {
            used[pos] = true
            edges[pos].forEach { (_, to) ->
                if (!used[to])
                    dfsPainter(to, used)
            }
        }

        fun simulate(automateInput: List<Int>): Boolean {
            startNodes.forEach {
                if (dfsSimulator(it, automateInput))
                    return true
            }
            return false
        }

        fun toDFA(): Automaton {
            fun List<Int>.toNode() =
                this.fold(0) { sum, cur -> sum + (1 shl cur) }

            val dfa = Automaton(
                2.0.pow(stateQuantity).toInt(),
                alphabetSize,
                listOf(startNodes.toNode())
            )
            for (mask in 1 until dfa.stateQuantity) {
                for (ch in 0 until alphabetSize) {
                    val edgeToSet = edges.filterIndexed{ index, _ -> (mask shr index) % 2 == 1 }
                        .flatMap { list -> list.filter { it.first == ch }.map { it.second } }
                        .toSet().toList()
                    if (edgeToSet.isNotEmpty())
                        dfa.edges[mask].add(Pair(ch, edgeToSet.toNode()))
                }
                for (v in 0 until stateQuantity)
                    dfa.isFinish[mask] = dfa.isFinish[mask] or (((mask shr v) % 2 == 1) and isFinish[v])
            }
            return dfa
        }

        fun isDFA(): Boolean {
            if (startNodes.size > 1)
                return false

            edges.forEach {
                if (it.size != it.distinctBy { edge -> edge.first }.size)
                    return false
            }

            return true
        }

        fun toDFAAndOptimize(): Automaton {
            val automaton =
                if (!isDFA())
                    toDFA()
                else
                    this

            val reachable = MutableList(automaton.stateQuantity) { false }
            automaton.dfsPainter(automaton.startNodes.first(), reachable)

            var sets = automaton.isFinish.mapIndexed { ind, finish -> Pair(ind, finish) }.filter{ reachable[it.first] }
                .groupBy { it.second }.map { group -> group.value.map { it.first } }
            val inSet = MutableList(automaton.stateQuantity) { -1 }
            sets.forEachIndexed { setNumber, set ->
                set.forEach { inSet[it] = setNumber }
            }

            var setsChanged = true
            while (setsChanged) {
                setsChanged = false
                for (symbol in 0 until automaton.alphabetSize) {
                    val newSets = mutableListOf<List<Int>>()
                    sets.forEach { set ->
                        val setPartition = List(sets.size + 1) { mutableListOf<Int>() }
                        set.forEach { state ->
                            val index = automaton.edges[state].binarySearch { it.first.compareTo(symbol) }
                            setPartition[
                                    if (index < 0) sets.size else inSet[automaton.edges[state][index].second]
                            ].add(state)
                        }
                        setsChanged = setsChanged or (setPartition.count { it.isNotEmpty() } > 1)
                        newSets.addAll(setPartition.filter { it.isNotEmpty() })
                        newSets.forEachIndexed { setNumber, curSet ->
                            curSet.forEach { inSet[it] = setNumber }
                        }
                    }
                    sets = newSets
                }
            }

            return Automaton(
                sets.size,
                alphabetSize,
                listOf(inSet[automaton.startNodes.first()]),
                List(sets.size) {
                    sets[it].flatMap {
                        state -> automaton.edges[state].map { edge -> Pair(edge.first, inSet[edge.second]) }
                            .filter { edge -> edge.second != -1 }
                    }.distinct().toMutableList()
                },
                MutableList(sets.size) { automaton.isFinish[sets[it].first()] }
            )
        }

        fun print() {
            println(stateQuantity)
            println(alphabetSize)
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

    fun readAutomaton(automatePath: String): Automaton {
        val automateScanner = Scanner(File(automatePath))
        val automaton = Automaton(
            automateScanner.nextLine().toInt(),
            automateScanner.nextLine().toInt(),
            getIntListFromScanner(automateScanner),
        )
        getIntListFromScanner(automateScanner).forEach { automaton.isFinish[it] = true }
        while (automateScanner.hasNextLine()) {
            val (from, symbol, to) = getIntListFromScanner(automateScanner)
            automaton.edges[from].add(Pair(symbol, to))
        }
        return automaton
    }
}