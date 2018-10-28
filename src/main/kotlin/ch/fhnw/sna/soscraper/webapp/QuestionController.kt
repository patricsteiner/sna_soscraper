package ch.fhnw.sna.soscraper.webapp

import ch.fhnw.sna.soscraper.domain.Owner
import ch.fhnw.sna.soscraper.domain.Question
import ch.fhnw.sna.soscraper.domain.QuestionRepository
import ch.fhnw.sna.soscraper.domain.TagRepository
import ch.fhnw.sna.soscraper.infrastructure.exporter.EdgeTableExporter
import ch.fhnw.sna.soscraper.infrastructure.exporter.NodeTableExporter
import ch.fhnw.sna.soscraper.infrastructure.services.SOScraper
import org.springframework.core.io.FileSystemResource
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
class QuestionController(val questionRepository: QuestionRepository, val tagRepository: TagRepository, val soScraper: SOScraper) {

    @GetMapping
    fun index(model: Model): String {
        val questions = questionRepository.findAll()
        model.addAttribute("questions", questions)
        return "index"
    }

    @GetMapping("/init")
    fun init(): String {
        questionRepository.save(Question(Owner("", 1, "", 1, null, "", 1), 0, "", 1, 1, 1, 1, 1, "", 1, listOf(), 1, 1, false, 1, 1, 1, 1))
        return "redirect:/"
    }

    @PostMapping("/scrape")
    fun scrape(@RequestParam firstId: Int, @RequestParam amount: Int): String {
        soScraper.scrape(firstId, amount)
        return "redirect:/"
    }

    @GetMapping("/export/nodes", produces = [MediaType.APPLICATION_OCTET_STREAM_VALUE])
    @ResponseBody
    fun exportNodes(): FileSystemResource {
        val nodeTableExporter = NodeTableExporter(questionRepository, tagRepository)
        return FileSystemResource(nodeTableExporter.export())
    }

    @GetMapping("/export/edges", produces = [MediaType.APPLICATION_OCTET_STREAM_VALUE])
    @ResponseBody
    fun exportEdges(): FileSystemResource {
        val edgeTableExporter = EdgeTableExporter(questionRepository, tagRepository)
        return FileSystemResource(edgeTableExporter.export())
    }

}
