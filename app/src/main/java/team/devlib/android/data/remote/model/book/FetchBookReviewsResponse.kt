package team.devlib.android.data.model.book

import com.google.gson.annotations.SerializedName

data class FetchBookReviewsResponse(
    val reviews: List<Review>
) {
    data class Review(
        val name: String,
        val content: String,
        @SerializedName("created_at") val createdAt: String,
        val score: Int,
    )
}
