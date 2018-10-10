class NetworkCreator {

    private var tags = hashMapOf<String, Int>()
    private var questionNodes = mutableListOf<String>()
    private var edges = mutableListOf<String>()

    fun addQuestionData(questionData: QuestionData) {
        questionNodes.add(questionDataToCsv(questionData))
        for (tag in questionData.tags) {
            if (!tags.containsKey(tag)) {
                tags.put(tag, tags.size)
            }
            edges.add(questionData.questionId.toString() + ";" + tags[tag])
        }
    }

    fun getQuestionNodes(): List<String> {
        return questionNodes
    }

    fun getTagNodes(): List<String> {
        val tagNodes = mutableListOf<String>()
        for (tag in tags) {
            tagNodes.add(tag.value.toString() + ";" + tag.key + ";;;;") // first col is id, 2nd label
        }
        return tagNodes
    }

    fun questionDataToCsv(questionData: QuestionData): String {
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
        return sb.toString()
    }
}