package ch.fhnw.sna.soscraper.infrastructure.exporter

import ch.fhnw.sna.soscraper.domain.QuestionRepository
import ch.fhnw.sna.soscraper.domain.TagRepository
import java.io.File

class OneModeEdgeTableExporter(private val questionRepository: QuestionRepository, private val tagRepository: TagRepository) {

    fun export(): File {
        val header = "Source;Target"
        val file = File("edges.csv")
        file.printWriter().use { out ->
            out.println(header)
            questionRepository.findAll().forEach { question ->
                for (i in 0 until question.tags.size) {
                    for (j in 0 until question.tags.size) {
                        if (i != j) {
                            val tagData1 = tagRepository.find(question.tags[i])
                                    ?: throw IllegalStateException("Make sure to create and fill the TagRepository first")
                            val tagData2 = tagRepository.find(question.tags[j])
                                    ?: throw IllegalStateException("Make sure to create and fill the TagRepository first")
                            out.println(tagData1.id.toString() + ";" + tagData2.id.toString())
                        }
                    }
                }
            }
        }
        return file
    }
}