interface SOScraperListener {
    fun receivedQuestionData(questionData: QuestionData)
    fun done()
}