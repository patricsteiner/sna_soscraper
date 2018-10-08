import org.json.JSONArray
import org.json.JSONObject
import java.time.Instant
import java.util.*

fun main(args: Array<String>) {
    val from = Instant.parse("2018-10-08T15:00:00Z")
    val to = Instant.parse("2018-10-08T15:00:10Z")
    val queue = LinkedList<QuestionData>()
    SOScraper(queue).scrape(from, to) // TODO thread for queue
}

class SOScraper(val outputQueue: Queue<QuestionData>) {

    val MIN_BACKOFF_SECONDS = 5
    val API_URL = "https://api.stackexchange.com/2.2/questions"

    val params = mutableMapOf(
            "site" to "stackoverflow",
            "sort" to "creation",
            "order" to "desc",
            "pagesize" to "100"
    )
    val stepSize = 10000 // in ms

    fun scrape(from: Instant, to: Instant) {
        var from = from.toEpochMilli()
        val to = to.toEpochMilli()
        while (from < to) {
            params["from"] = from.toString()
            params["to"] = (from + stepSize).toString()
            val response = khttp.get(url = API_URL, params = params)
            val json = response.jsonObject
            println(json)

            for (item in json["items"] as JSONArray) {
                outputQueue.offer(mapToQuestionData(item as JSONObject))
            }

            from += stepSize
            val backoff = (if (json.has("backoff")) json.getInt("backoff") else MIN_BACKOFF_SECONDS) * 1000
            println("has_more = ${json.getBoolean("has_more")}")
            Thread.sleep(backoff.toLong())
        }
    }

    fun mapToQuestionData(json: JSONObject): QuestionData {
        val tags = arrayListOf<String>()
        json.getJSONArray("tags").forEach({ tags.add(it as String) })
        return QuestionData(
                id = json.getLong("question_id"),
                title = json.getString("title"),
                link = json.getString("link"),
                score = json.getInt("score"),
                isAnswered = json.getBoolean("is_answered"),
                answerCount = json.getInt("answer_count"),
                creationDate = json.getLong("creation_date"),
                viewCount = json.getInt("view_count"),
                userId = json.getJSONObject("owner").getLong("user_id"),
                userName = json.getJSONObject("owner").getString("display_name"),
                userReputation = json.getJSONObject("owner").getInt("reputation"),
                tags = tags
        )
    }
}