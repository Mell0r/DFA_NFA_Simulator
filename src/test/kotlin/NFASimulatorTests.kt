import org.junit.jupiter.api.Test
import java.io.File
import java.util.*
import kotlin.test.assertEquals
import Utility.getIntListFromScanner
import Utility.readAutomate

class NFASimulatorTests {
    private val AUTOMATES_LOCATION = "testData/automates/"
    private val INPUTS_LOCATION = "testData/inputs/"

    fun test(testFilename: String) {
        val scanner = Scanner(File(INPUTS_LOCATION + testFilename))
        while (scanner.hasNextLine()) {
            assertEquals(
                scanner.nextLine().toBoolean(),
                readAutomate(AUTOMATES_LOCATION + testFilename).simulate(getIntListFromScanner(scanner))
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