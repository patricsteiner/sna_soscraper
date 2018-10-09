import org.json.JSONArray
import org.json.JSONObject
import java.util.*
import java.util.logging.Logger

class SOScraper(val outputQueue: Queue<QuestionData>) {

    val logger = Logger.getLogger("SOScraper")

    val API_URL = "https://api.stackexchange.com/2.2/questions/"
    val MIN_BACKOFF_MS = 5000

    val params = mutableMapOf(
            "site" to "stackoverflow",
            "sort" to "creation",
            "order" to "desc",
            "pagesize" to "100", // max is 100
            "filter" to "!gB7l(.eUN4A78AG1cjy.Zxgd3gyfuKaZ(XE" // this is a custom filter created on the stackexchange api doc website
    )

    fun scrape(firstId: Int, totalIds: Int) {
        var currentId = firstId
        val idsPerRequest = 70 // theoretical max is 100, but then URL can get too long TODO find out how high i can set this before error 400
        var idsRequested = 0
        while (idsRequested < totalIds) {
            val ids = (currentId..currentId + idsPerRequest).joinToString(separator = ";")
            val response = khttp.get(url = API_URL + ids, params = params)
            idsRequested += idsPerRequest
            currentId += idsPerRequest
            logger.info("requested $idsPerRequest ids, from $currentId to ${currentId+idsPerRequest} (total $idsRequested out of $totalIds)")

            val json = response.jsonObject
            println(json.toString())

            var queuedIds = 0
            for (item in json["items"] as JSONArray) {
                outputQueue.offer(mapToQuestionData(item as JSONObject))
                queuedIds++
            }
            logger.info("offered $queuedIds items to queue, no results found for ${idsPerRequest-queuedIds} ids")

            //if (json.has("has_more")) logger.info("has_more = ${json.getBoolean("has_more")}") this should always be false if we query for specific ids
            if (json.has("quota_remaining")) logger.info("quota_remaining = ${json.getInt("quota_remaining")}")

            var backoff = MIN_BACKOFF_MS
            if (json.has("backoff")) {
                backoff = json.getInt("backoff") * 1000
                logger.info("backoff = ${json.getInt("backoff")}")
            }
            Thread.sleep(backoff.toLong())
        }
    }

    // TODO map more stuff or just use the complete json object
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