package exporter

import data.QuestionRepository
import data.TagRepository
import java.io.File

class EdgeTableExporter(private val questionRepository: QuestionRepository, private val tagRepository: TagRepository) {

    private val header = "Source;Target"

    fun export(path: String) {
        File(path).printWriter().use { out ->
            out.println(header)
            questionRepository.findAll().forEach {question ->
                question.tags.forEach { tag ->
                    out.println(question.questionId.toString() + ";" + tagRepository.find(tag))
                }
            }
        }
    }

}