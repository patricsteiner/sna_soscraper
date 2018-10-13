package exporter

import data.QuestionRepository
import data.TagRepository
import java.io.File

class OneModeEdgeTableExporter(val questionRepository: QuestionRepository, val tagRepository: TagRepository) {

    fun export(path: String) {
        val header = "Source;Target"
        File(path).printWriter().use { out ->
            out.println(header)
            questionRepository.findAll().forEach { question ->
                for (i in 0 until question.tags.size) {
                    for (j in 0 until question.tags.size) {
                        if (i != j) {
                            out.println(tagRepository.find(question.tags[i]).toString() + ";" + tagRepository.find(question.tags[j]).toString())
                        }
                    }
                }
            }
        }
    }
}