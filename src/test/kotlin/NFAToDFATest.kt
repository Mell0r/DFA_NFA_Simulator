import Utility.readAutomate
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class NFAToDFATest {
    private fun generateRandomInput(m: Int) = List((1..20).random()) { (0 until m).random() }

    private val AUTOMATES_LOCATION = "testData/automates/"

    fun hugeRandomTest(testFilename: String) {
        val originalAutomate = readAutomate(AUTOMATES_LOCATION + testFilename)
        val dfa = originalAutomate.toDFA()
        for (t in 1..100) {
            val randomInput = generateRandomInput(originalAutomate.m)
            assertEquals(
                originalAutomate.simulate(randomInput),
                dfa.simulate(randomInput)
            )
        }
    }

    @Test
    fun tinyNFA() {
        hugeRandomTest("tinyNFA.txt")
    }

    @Test
    fun sample() {
        hugeRandomTest("sample.txt")
    }

    @Test
    fun `2,3-ab`() {
        hugeRandomTest("2,3-ab.txt")
    }

    @Test
    fun abbba() {
        hugeRandomTest("abbba.txt")
    }
}