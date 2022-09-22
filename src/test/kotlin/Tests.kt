import org.junit.jupiter.api.Test
import java.io.File
import java.util.*
import kotlin.test.assertEquals

class Tests {
    private val AUTOMATES_LOCATION = "testData/automates/"
    private val INPUTS_LOCATION = "testData/inputs/"

    fun test(testFilename: String) {
        val input = Scanner(File(INPUTS_LOCATION + testFilename))
        while (input.hasNextLine()) {
            val sInp = getIntListFromScanner(input)
            assertEquals(
                input.nextLine().toBoolean(),
                simulateAutomate(AUTOMATES_LOCATION + testFilename, sInp)
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