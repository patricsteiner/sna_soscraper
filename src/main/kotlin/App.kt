import java.util.*

fun main(args: Array<String>) {
    val firstId = 52720304
    val totalIds = 1000
    val queue = LinkedList<QuestionData>()
    Thread { SOScraper(queue).scrape(firstId, totalIds) }.start()
    while (true) {
        if (queue.isNotEmpty()) {
            //println(queue.poll()) // TODO save this stuff to mongoDB or sth
        }
        Thread.sleep(100)
    }
}