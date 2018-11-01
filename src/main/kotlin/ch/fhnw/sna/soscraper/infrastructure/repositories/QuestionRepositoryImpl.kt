package ch.fhnw.sna.soscraper.infrastructure.repositories

import ch.fhnw.sna.soscraper.domain.Question
import ch.fhnw.sna.soscraper.domain.QuestionRepository
import org.springframework.stereotype.Repository

@Repository
class QuestionRepositoryImpl(val mongoQuestionRepository: MongoQuestionRepository) : QuestionRepository {

    override fun findAll(): List<Question> {
        return mongoQuestionRepository.findAll()
    }

    override fun save(question: Question) {
        mongoQuestionRepository.save(question)
    }

    fun findMaxId(): Long {
        return mongoQuestionRepository.findFirstByOrderByQuestionIdDesc().questionId
    }

    fun findTop100(): List<Question> {
        return mongoQuestionRepository.findTop100ByOrderByQuestionIdDesc()
    }

}