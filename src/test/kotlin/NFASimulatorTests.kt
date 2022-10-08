import TestUtility.AUTOMATES_LOCATION
import TestUtility.INPUTS_LOCATION
import org.junit.jupiter.api.Test
import java.io.File
import java.util.*
import kotlin.test.assertEquals
import Utility.getIntListFromScanner
import Utility.readAutomaton

class NFASimulatorTests {
    fun test(testFilename: String) {
        val scanner = Scanner(File(INPUTS_LOCATION + testFilename))
        while (scanner.hasNextLine()) {
            assertEquals(
                scanner.nextLine().toBoolean(),
                readAutomaton(AUTOMATES_LOCATION + testFilename).simulate(getIntListFromScanner(scanner))
            )
        }
    }

    @Test
    fun sample() {
        test("sample.txt")
    }

    @Test
    fun `2,3-ab`() {
        test("2,3-ab.txt")
    }

    @Test
    fun abbba() {
        test("abbba.txt")
    }
}