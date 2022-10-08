import TestUtility.AUTOMATES_LOCATION
import TestUtility.generateRandomInput
import Utility.readAutomaton
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class NFAToDFATest {
    fun hugeRandomTest(testFilename: String) {
        val originalAutomate = readAutomaton(AUTOMATES_LOCATION + testFilename)
        val dfa = originalAutomate.toDFA()
        for (t in 1..100) {
            val randomInput = generateRandomInput(originalAutomate.alphabetSize)
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