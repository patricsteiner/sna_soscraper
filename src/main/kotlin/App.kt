import data.Question
import data.QuestionRepository
import data.TagRepository
import exporter.EdgeTableExporter
import exporter.NodeTableExporter
import scraper.SOScraper
import scraper.SOScraperListener

fun main(args: Array<String>) {
    App.run()
}

object App : SOScraperListener {

    val questionRepository = QuestionRepository()
    val tagRepository = TagRepository()

    fun run() {
        val firstId = 52720304
        val totalIds = 150
        val scraper = SOScraper(this)
        scraper.scrape(firstId, totalIds)
    }

    override fun receivedQuestion(question: Question) {
        questionRepository.save(question)
        question.tags.forEach { tagRepository.save(it) }
    }

    override fun done() {
        val nodeTableExporter = NodeTableExporter(questionRepository, tagRepository)
        nodeTableExporter.export("nodes.csv")
        val edgeTableExporter = EdgeTableExporter(questionRepository, tagRepository)
        edgeTableExporter.export("edges.csv")
    }
}