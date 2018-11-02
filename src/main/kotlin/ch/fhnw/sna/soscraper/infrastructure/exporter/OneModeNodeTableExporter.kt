package ch.fhnw.sna.soscraper.infrastructure.exporter

import ch.fhnw.sna.soscraper.domain.TagRepository
import java.io.File

class OneModeNodeTableExporter(private val tagRepository: TagRepository) {

    fun export() : File {
        val header = "Id;Label;occurrence;views;answered;unanswered;answeredRatio;bounty"
        val file = File("nodes.csv")
        file.printWriter().use { out ->
            out.println(header)
            tagRepository.findAll().forEach {
                val unanswered = it.value.occurrence - it.value.answered
                val answeredRatio = it.value.answered.toFloat() / it.value.occurrence
                out.println(it.value.id.toString() + ";"
                        + it.key + ";"
                        + it.value.occurrence.toString() + ";"
                        + it.value.views.toString() + ";"
                        + it.value.answered.toString() + ";"
                        + unanswered.toString() + ";"
                        + answeredRatio.toString() + ";"
                        + it.value.bounty.toString())
            }
        }
        return file
    }

}