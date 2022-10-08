object TestUtility {
    fun generateRandomInput(m: Int) = List((1..20).random()) { (0 until m).random() }

    val AUTOMATES_LOCATION = "testData/automates/"
    val INPUTS_LOCATION = "testData/inputs/"
}