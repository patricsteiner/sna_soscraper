import java.util.*

fun main(args: Array<String>) {
    val firstId = 52720304
    val totalIds = 150
    val queue = LinkedList<QuestionData>()
    Thread { SOScraper(queue).scrape(firstId, totalIds) }.start()

    val networkCreator = NetworkCreator()

    while (true) {
        if (queue.isNotEmpty()) {
            networkCreator.addQuestionData(queue.poll())
        }
        Thread.sleep(100)
    }
}
