package team.devlib.android.data.remote.model.question

import com.google.gson.annotations.SerializedName

data class CreateReplyRequest(
    val content: String,
    @SerializedName("book_id") val bookId: Long,
)