package exporter

import data.TagRepository
import java.io.File

class OneModeNodeTableExporter(val tagRepository: TagRepository) {

    fun export(path: String) {
        val header = "Id;Label"
        File(path).printWriter().use { out ->
            out.println(header)
            tagRepository.findAll().forEach {
                out.println(it.value.toString() + ";" + it.key)
            }
        }
    }

}