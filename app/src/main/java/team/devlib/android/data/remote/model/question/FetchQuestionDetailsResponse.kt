package team.devlib.android.data.remote.model.question

data class FetchQuestionDetailsResponse(
    val title: String,
    val content: String,
    val author: String,
)