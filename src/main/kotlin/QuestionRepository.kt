class QuestionRepository() {

    private val data = mutableListOf<QuestionData>()

    fun save(questionData: QuestionData) {
        data.add(questionData)
    }

    fun findAll(): List<QuestionData> {
        return data
    }

}