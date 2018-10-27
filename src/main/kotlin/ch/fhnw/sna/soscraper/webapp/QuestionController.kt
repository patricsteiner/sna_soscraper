package ch.fhnw.sna.soscraper.webapp

import ch.fhnw.sna.soscraper.domain.Owner
import ch.fhnw.sna.soscraper.domain.Question
import ch.fhnw.sna.soscraper.domain.QuestionRepository
import ch.fhnw.sna.soscraper.domain.TagRepository
import ch.fhnw.sna.soscraper.infrastructure.services.SOScraper
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
class QuestionController(val questionRepository: QuestionRepository, val tagRepository: TagRepository, val soScraper: SOScraper) {

    @GetMapping
    fun index(model : Model): String {
        val questions = questionRepository.findAll()
        model.addAttribute("questions", questions)
        return "index"
    }

    @GetMapping("/init")
    fun init(): String {
        questionRepository.save(Question(Owner("", 1, "", 1, null, "", 1), 0, "", 1, 1, 1, 1, 1, "", 1, listOf(), 1, 1, false, 1, 1, 1, 1))
        return "redirect:/"
    }

    @GetMapping("/scrape")
    fun scrape(@RequestParam("id", defaultValue = "52720000") id: Int, @RequestParam("amount", defaultValue = "10") amount: Int): String {
        soScraper.scrape(id, amount)
        return "redirect:/"
    }

}
