import java.util.*

fun main(args: Array<String>) {
    val firstId = 52720304
    val totalIds = 1000
    val queue = LinkedList<QuestionData>()
    Thread { SOScraper(queue).scrape(firstId, totalIds) }.start()
    while (true) {
        if (queue.isNotEmpty()) {
            //println(minimizeQuestionData(queue.poll())) // TODO save this stuff to mongoDB or sth
        }
        Thread.sleep(100)
    }
}

fun minimizeQuestionData(questionData: QuestionData): String {
    val sb = StringBuilder()
    sb.append(questionData.questionId)
    sb.append(";")
    sb.append(questionData.title)
    sb.append(";")
    sb.append(questionData.answerCount)
    sb.append(";")
    sb.append(questionData.isAnswered)
    sb.append(";")
    sb.append(questionData.score)
    sb.append(";")
    sb.append(questionData.viewCount)
    sb.append(";")
    sb.append(questionData.tags)
    return sb.toString()
}