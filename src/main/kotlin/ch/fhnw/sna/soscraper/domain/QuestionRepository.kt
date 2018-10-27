package ch.fhnw.sna.soscraper.domain

interface QuestionRepository {

    fun save(question: Question)
    fun findAll() : List<Question>

}