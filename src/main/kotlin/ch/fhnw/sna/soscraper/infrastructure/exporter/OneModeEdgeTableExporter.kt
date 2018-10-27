package ch.fhnw.sna.soscraper.infrastructure.exporter

import ch.fhnw.sna.soscraper.domain.QuestionRepository
import ch.fhnw.sna.soscraper.domain.TagRepository
import java.io.File

class OneModeEdgeTableExporter(private val questionRepository: QuestionRepository, private val tagRepository: TagRepository) {

    fun export(path: String) {
        val header = "Source;Target"
        File(path).printWriter().use { out ->
            out.println(header)
            questionRepository.findAll().forEach { question ->
                for (i in 0 until question.tags.size) {
                    for (j in 0 until question.tags.size) {
                        if (i != j) {
                            out.println(tagRepository.find(question.tags[i])!!.id.toString() + ";" + tagRepository.find(question.tags[j])!!.id.toString())
                        }
                    }
                }
            }
        }
    }
}