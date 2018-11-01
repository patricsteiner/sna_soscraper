package ch.fhnw.sna.soscraper.infrastructure.repositories

import ch.fhnw.sna.soscraper.domain.Question
import org.springframework.data.mongodb.repository.MongoRepository

interface MongoQuestionRepository : MongoRepository<Question, Long> {
    fun findFirstByOrderByQuestionIdDesc(): Question
    fun findTop100ByOrderByQuestionIdDesc() : List<Question>
}
