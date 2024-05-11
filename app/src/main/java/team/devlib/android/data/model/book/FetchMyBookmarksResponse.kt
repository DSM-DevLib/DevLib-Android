package team.devlib.android.data.model.book

import com.google.gson.annotations.SerializedName

data class FetchMyBookmarksResponse(
    @SerializedName("bookmarks") val bookmarks: List<Bookmark>
) {
    data class Bookmark(
        @SerializedName("id") val id: Long,
        val name: String,
        val author: String,
        val cover: String,
        @SerializedName("is_marked") val isMarked: Boolean,
    )
}
