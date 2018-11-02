package ch.fhnw.sna.soscraper.infrastructure.services

import ch.fhnw.sna.soscraper.domain.Question
import ch.fhnw.sna.soscraper.domain.QuestionRepository
import ch.fhnw.sna.soscraper.domain.TagRepository
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import com.fasterxml.jackson.module.kotlin.readValue
import org.json.JSONArray
import org.json.JSONObject
import org.springframework.stereotype.Service
import java.util.logging.Logger

@Service
class SOScraper(val questionRepository: QuestionRepository, val tagRepository: TagRepository) {

    private val logger = Logger.getLogger("ch.fhnw.sna.soscraper.infrastructure.services.SOScraper")

    private val API_URL = "https://api.stackexchange.com/2.2/questions/"
    private val MIN_BACKOFF_MS = 100

    private val params = mutableMapOf(
            "site" to "stackoverflow",
            "sort" to "creation",
            "order" to "desc",
            "pagesize" to "100", // max is 100
            "filter" to "!gB7l(.eUN4A78AG1cjy.Zxgd3gyfuKaZ(XE" // this is a custom filter created on the stackexchange api doc website that delivers the data as defined in Question.kt
    )

    fun scrape(firstId: Int, amount: Int = 99) {
        var currentId = firstId
        val idsPerRequest = 99 // theoretical max is 100, but then URL can get too long, so i just use 99
        var totalReceivedQuestions = 0
        while (totalReceivedQuestions < amount) {
            val ids = (currentId..currentId + idsPerRequest).joinToString(separator = ";")
            val response = khttp.get(url = API_URL + ids, params = params)
            currentId += idsPerRequest

            val json = response.jsonObject
            println(json.toString())

            var receivedQuestions = 0
            for (item in json["items"] as JSONArray) {
                try {
                    handleQuestion(item as JSONObject)
                    receivedQuestions++
                    if (receivedQuestions + totalReceivedQuestions >= amount) {
                        break
                    }
                } catch (e: MissingKotlinParameterException) {
                    // ignore it when there is incomplete information
                }
            }
            totalReceivedQuestions += receivedQuestions
            logger.info("Received $receivedQuestions questions, no results found for ${idsPerRequest - receivedQuestions} ids. Total received: $totalReceivedQuestions")

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

    private fun handleQuestion(item: JSONObject) {
        val mapper = ObjectMapper().registerModule(KotlinModule())
        val question : Question = mapper.readValue(item.toString())
        questionRepository.save(question)
    }

}