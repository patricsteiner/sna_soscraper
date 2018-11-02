package ch.fhnw.sna.soscraper.infrastructure.exporter

import ch.fhnw.sna.soscraper.domain.QuestionRepository
import ch.fhnw.sna.soscraper.domain.TagRepository
import java.io.File

class EdgeTableExporter(private val questionRepository: QuestionRepository, private val tagRepository: TagRepository) {

    private val header = "Source;Target"

    fun export() : File {
        val file = File("edges.csv")
        file.printWriter().use { out ->
            out.println(header)
            questionRepository.findAll().forEach {question ->
                question.tags.forEach { tag ->
                    val tagData = tagRepository.find(tag) ?: throw IllegalStateException("Make sure to create and fill the TagRepository first")
                    out.println(question.questionId.toString() + ";" + tagData.id)
                }
            }
        }
        return file
    }

}