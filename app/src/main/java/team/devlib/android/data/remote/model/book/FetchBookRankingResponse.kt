package team.devlib.android.data.remote.model.book

import com.google.gson.annotations.SerializedName

data class FetchBookRankingResponse(
    @SerializedName("books") val books: List<Book>
) {
    data class Book(
        val rank: Int,
        val id: Long,
        val name: String,
        val author: String,
        val cover: String,
        val score: Float,
        @SerializedName("review_count") val reviewCount: Int,
    )
}
