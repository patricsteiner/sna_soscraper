package exporter

import data.TagRepository
import java.io.File

class OneModeNodeTableExporter(private val tagRepository: TagRepository) {

    fun export(path: String) {
        val header = "Id;Label;occurence;views;answered;unanswered;answeredRatio;bounty"
        File(path).printWriter().use { out ->
            out.println(header)
            tagRepository.findAll().forEach {
                val unanswered = it.value.occurence - it.value.answered
                val answeredRatio = it.value.answered.toFloat()/it.value.occurence
                out.println(it.value.id.toString() + ";"
                        + it.key + ";"
                        + it.value.occurence.toString() + ";"
                        + it.value.views.toString() + ";"
                        + it.value.answered.toString() + ";"
                        + unanswered.toString() + ";"
                        + answeredRatio.toString() + ";"
                        + it.value.bounty.toString())
            }
        }
    }

}