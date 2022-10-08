import TestUtility.AUTOMATES_LOCATION
import TestUtility.generateRandomInput
import Utility.readAutomaton
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class DFAOptimizationTest {
    fun randomTestWithCheck(testFilename: String) {
        val originalAutomate = readAutomaton(AUTOMATES_LOCATION + testFilename)
        val dfa = originalAutomate.toDFAAndOptimize()
        assertTrue { dfa.isDFA() }
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
        randomTestWithCheck("tinyNFA.txt")
    }

    @Test
    fun sample() {
        randomTestWithCheck("sample.txt")
    }

    @Test
    fun `2,3-ab`() {
        randomTestWithCheck("2,3-ab.txt")
    }

    @Test
    fun abbba() {
        randomTestWithCheck("abbba.txt")
    }

    @Test
    fun detailed_test() {
        randomTestWithCheck("detailedDFAToOptimize.txt")

        val dfa = readAutomaton(AUTOMATES_LOCATION + "detailedDFAToOptimize.txt").toDFAAndOptimize()
        for (t in 1..1000) {
            val randomInput = generateRandomInput(dfa.alphabetSize)
            assertEquals(randomInput.count { it == 0 } % 3 == 0, dfa.simulate(randomInput))
        }
    }
}