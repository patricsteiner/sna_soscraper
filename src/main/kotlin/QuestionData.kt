import java.time.LocalDateTime

data class QuestionData(
        val id: Long,
        val title: String,
        val link: String,
        val score: Int,
        //val commentCount: Int, TODO
        val viewCount: Int,
        val isAnswered: Boolean,
        val answerCount: Int,
        val tags: List<String>,
        val creationDate: Long,
        //val anwserAccepted: Long, TODO
        val userId: Long,
        val userName: String,
        val userReputation: Int
)