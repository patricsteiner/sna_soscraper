package data

class QuestionRepository() {

    private val data = mutableListOf<Question>()

    fun save(question: Question) {
        data.add(question)
    }

    fun findAll(): List<Question> {
        return data
    }

}