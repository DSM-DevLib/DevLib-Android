package team.devlib.android.data.remote.model.question

import com.google.gson.annotations.SerializedName

data class FetchQuestionDetailsResponse(
    val title: String,
    val content: String,
    val author: String,
    val mine: Boolean,
    @SerializedName("reply_list") val replyList: List<Reply>,
) {
    data class Reply(
        @SerializedName("created_date") val createdDate: String,
        val username: String,
        @SerializedName("like_count") val likeCount: Int,
        val content: String,
        @SerializedName("book_id") val bookId: Long,
        val mine: Boolean,
    )
}