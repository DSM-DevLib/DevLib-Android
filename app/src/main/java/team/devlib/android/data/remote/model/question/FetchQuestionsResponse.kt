package team.devlib.android.data.remote.model.question

import com.google.gson.annotations.SerializedName

data class FetchQuestionsResponse(
    @SerializedName("questions") val questions: List<Question>
) {
    data class Question(
        val username: String,
        val title: String,
        val createdDate: String,
    )
}