package ch.fhnw.sna.soscraper.webapp

import ch.fhnw.sna.soscraper.domain.TagRepository
import ch.fhnw.sna.soscraper.infrastructure.exporter.EdgeTableExporter
import ch.fhnw.sna.soscraper.infrastructure.exporter.NodeTableExporter
import ch.fhnw.sna.soscraper.infrastructure.exporter.OneModeEdgeTableExporter
import ch.fhnw.sna.soscraper.infrastructure.exporter.OneModeNodeTableExporter
import ch.fhnw.sna.soscraper.infrastructure.repositories.QuestionRepositoryImpl
import ch.fhnw.sna.soscraper.infrastructure.services.SOScraper
import org.springframework.core.io.FileSystemResource
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class QuestionController(val questionRepository: QuestionRepositoryImpl, val tagRepository: TagRepository, val soScraper: SOScraper) {

    @GetMapping
    fun index(model: Model): String {
        val questions = questionRepository.findTop100()
        val maxId = questionRepository.findMaxId()
        model.addAttribute("maxId", maxId)
        model.addAttribute("questions", questions)
        return "index"
    }

    @PostMapping("/scrape")
    fun scrape(@RequestParam firstId: Int, @RequestParam amount: Int): String {
        soScraper.scrape(firstId, amount)
        return "redirect:/"
    }

    @GetMapping("/export/nodes", produces = [MediaType.APPLICATION_OCTET_STREAM_VALUE])
    @ResponseBody
    fun exportNodes(): FileSystemResource {
        fillTagRepository()
        val nodeTableExporter = OneModeNodeTableExporter(tagRepository)
        return FileSystemResource(nodeTableExporter.export())
    }

    @GetMapping("/export/edges", produces = [MediaType.APPLICATION_OCTET_STREAM_VALUE])
    @ResponseBody
    fun exportEdges(): FileSystemResource {
        fillTagRepository()
        val edgeTableExporter = OneModeEdgeTableExporter(questionRepository, tagRepository)
        return FileSystemResource(edgeTableExporter.export())
    }

    private fun fillTagRepository() {
        questionRepository.findAll().forEach { question ->
            question.tags.forEach {
                tagRepository.save(
                        it,
                        question.viewCount,
                        question.isAnswered,
                        question.bountyAmount
                )
            }
        }
    }

}
