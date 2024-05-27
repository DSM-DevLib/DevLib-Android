package team.devlib.android.data.remote.model.question

import com.google.gson.annotations.SerializedName

data class FetchQuestionsResponse(
    @SerializedName("questions") val questions: List<Question>
) {
    data class Question(
        @SerializedName("question_id") val questionId: Long,
        val username: String,
        val title: String,
        @SerializedName("created_date") val createdDate: String,
    )
}