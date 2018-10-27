package ch.fhnw.sna.soscraper.infrastructure.exporter

import ch.fhnw.sna.soscraper.domain.Question
import ch.fhnw.sna.soscraper.domain.QuestionRepository
import ch.fhnw.sna.soscraper.domain.TagRepository
import java.io.File

class NodeTableExporter(private val questionRepository: QuestionRepository, private val tagRepository: TagRepository) {

    fun export(path: String) {
        File(path).printWriter().use { out ->
            out.println(header)
            tagRepository.findAll().forEach {
                out.println(it.value.toString() + ";" + it.key + ";tag;;;;;;")
            }
            questionRepository.findAll().forEach {
                out.println(questionToCsv(it))
            }
        }
    }

    // header must correlate with fun questionToCsv + Id/Label at the start (Gephi convention) + type (question/tag)
    private val header = "Id;Label;type;title;creationDate;answerCount;isAnswered;score;viewCount"

    private fun questionToCsv(question: Question): String {
        val sb = StringBuilder()
        sb.append(question.questionId)
        sb.append(";;question;")
        sb.append(question.title)
        sb.append(";")
        sb.append(question.creationDate)
        sb.append(";")
        sb.append(question.answerCount)
        sb.append(";")
        sb.append(question.isAnswered)
        sb.append(";")
        sb.append(question.score)
        sb.append(";")
        sb.append(question.viewCount)
        return sb.toString()
    }

}