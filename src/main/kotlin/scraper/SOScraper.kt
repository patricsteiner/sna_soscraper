package scraper

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import com.fasterxml.jackson.module.kotlin.readValue
import data.Question
import org.json.JSONArray
import org.json.JSONObject
import java.util.logging.Logger

class SOScraper(val soScraperListener: SOScraperListener) {

    private val logger = Logger.getLogger("scraper.SOScraper")

    private val API_URL = "https://api.stackexchange.com/2.2/questions/"
    private val MIN_BACKOFF_MS = 100

    private val params = mutableMapOf(
            "site" to "stackoverflow",
            "sort" to "creation",
            "order" to "desc",
            "pagesize" to "100", // max is 100
            "filter" to "!gB7l(.eUN4A78AG1cjy.Zxgd3gyfuKaZ(XE" // this is a custom filter created on the stackexchange api doc website that delivers the data as defined in Question.kt
    )

    fun scrape(firstId: Int, totalIds: Int) { // TODO only return when there are n questions found (as opposed to n tries)
        var currentId = firstId
        val idsPerRequest = 99 // theoretical max is 100, but then URL is too long, so i just use 99
        var idsRequested = 0
        while (idsRequested < totalIds) {
            val ids = (currentId..currentId + idsPerRequest).joinToString(separator = ";")
            val response = khttp.get(url = API_URL + ids, params = params)
            idsRequested += idsPerRequest
            currentId += idsPerRequest
            logger.info("requested $idsPerRequest ids, from $currentId to ${currentId + idsPerRequest} (total $idsRequested out of $totalIds)")

            val json = response.jsonObject
            println(json.toString())

            var nReceivedQuestions = 0
            for (item in json["items"] as JSONArray) {
                try {
                    val question = mapToQuestionData(item as JSONObject)
                    soScraperListener.receivedQuestion(question)
                    nReceivedQuestions++
                } catch (e: MissingKotlinParameterException) {
                    // ignore it when there is incomplete information
                }
            }
            logger.info("received $nReceivedQuestions items, no results found for ${idsPerRequest - nReceivedQuestions} ids")

            //if (json.has("has_more")) logger.info("has_more = ${json.getBoolean("has_more")}") this should always be false if we query for specific ids
            if (json.has("quota_remaining")) logger.info("quota_remaining = ${json.getInt("quota_remaining")}")

            var backoff = MIN_BACKOFF_MS
            if (json.has("backoff")) {
                backoff = json.getInt("backoff") * 1000
                logger.info("backoff = ${json.getInt("backoff")}")
            }
            Thread.sleep(backoff.toLong())
        }
        soScraperListener.done()
    }

    private fun mapToQuestionData(json: JSONObject): Question {
        val mapper = ObjectMapper().registerModule(KotlinModule())
        return mapper.readValue(json.toString())
    }
}