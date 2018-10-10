package scraper

import data.Question

interface SOScraperListener {
    fun receivedQuestion(question: Question)
    fun done()
}