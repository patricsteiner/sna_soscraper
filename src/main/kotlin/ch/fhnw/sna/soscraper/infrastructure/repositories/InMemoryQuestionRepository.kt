package ch.fhnw.sna.soscraper.infrastructure.repositories

import ch.fhnw.sna.soscraper.domain.Question
import ch.fhnw.sna.soscraper.domain.QuestionRepository
import org.springframework.stereotype.Repository

@Repository
class InMemoryQuestionRepository : QuestionRepository {

    private val data = mutableListOf<Question>()

    override fun save(question: Question) {
        data.add(question)
    }

    override fun findAll(): List<Question> {
        return data
    }

}