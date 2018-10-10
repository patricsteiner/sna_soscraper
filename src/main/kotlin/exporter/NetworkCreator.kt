package exporter

import data.Question

class NetworkCreator {

    private var tags = HashMap<String, Int>()
    private var questionNodes = mutableListOf<String>()
    private var edges = mutableListOf<String>()

    fun addQuestionData(question: Question) {
        questionNodes.add(questionDataToCsv(question))
        for (tag in question.tags) {
            if (!tags.containsKey(tag)) {
                tags.put(tag, tags.size)
            }
            edges.add(question.questionId.toString() + ";" + tags[tag])
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

    fun getEdges(): List<String> {
        return edges
    }

    private fun questionDataToCsv(question: Question): String {
        val sb = StringBuilder()
        sb.append(question.questionId)
        sb.append(";")
        sb.append(question.title)
        sb.append(";")
        sb.append(question.answerCount)
        sb.append(";")
        sb.append(question.isAnswered)
        sb.append(";")
        sb.append(question.score)
        sb.append(";")
        sb.append(question.viewCount)
        return sb.toString()
    }
}