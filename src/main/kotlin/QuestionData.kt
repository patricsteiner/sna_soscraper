import com.fasterxml.jackson.annotation.JsonProperty

data class QuestionData(
    @JsonProperty("owner") val owner: Owner?,
    @JsonProperty("comment_count") val commentCount: Int?,
    @JsonProperty("link") val link: String?,
    @JsonProperty("last_edit_date") val lastEditDate: Long?,
    @JsonProperty("last_activity_date") val lastActivityDate: Long?,
    @JsonProperty("creation_date") val creationDate: Long?,
    @JsonProperty("answer_count") val answerCount: Int?,
    @JsonProperty("accepted_answer_id") val acceptedAnswerId: Long?,
    @JsonProperty("title") val title: String?,
    @JsonProperty("question_id") val questionId: Long?,
    @JsonProperty("tags") val tags: List<String?>?,
    @JsonProperty("score") val score: Int?,
    @JsonProperty("favorite_count") val favoriteCount: Int?,
    @JsonProperty("is_answered") val isAnswered: Boolean?,
    @JsonProperty("close_vote_count") val closeVoteCount: Int?,
    @JsonProperty("view_count") val viewCount: Long?
)

data class Owner(
    @JsonProperty("user_type") val userType: String?,
    @JsonProperty("user_id") val userId: Int?,
    @JsonProperty("link") val link: String?,
    @JsonProperty("reputation") val reputation: Int?,
    @JsonProperty("badge_counts") val badgeCounts: BadgeCounts?,
    @JsonProperty("display_name") val displayName: String?,
    @JsonProperty("accept_rate") val acceptRate: Int?
)

data class BadgeCounts(
    @JsonProperty("gold") val gold: Int?,
    @JsonProperty("silver") val silver: Int?,
    @JsonProperty("bronze") val bronze: Int?
)