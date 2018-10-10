import java.io.File

fun main(args: Array<String>) {
    App.run()
}

object App : SOScraperListener {

    val networkCreator = NetworkCreator()

    fun run() {
        val firstId = 52720304
        val totalIds = 150
        val scraper = SOScraper(this)
        scraper.scrape(firstId, totalIds)
    }

    override fun receivedQuestionData(questionData: QuestionData) {
        networkCreator.addQuestionData(questionData)
    }

    override fun done() {
        File("nodes.csv").printWriter().use { out ->
            out.println("Id;title;answerCount;isAnswered;score;viewCount")
            networkCreator.getQuestionNodes().forEach {
                out.println(it)
            }
            networkCreator.getTagNodes().forEach {
                out.println(it)
            }
        }
        File("edges.csv").printWriter().use { out ->
            out.println("Source;Target")
            networkCreator.getEdges().forEach {
                out.println(it)
            }
        }
    }
}