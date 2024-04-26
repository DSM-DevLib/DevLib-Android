package team.devlib.android.data.api

import retrofit2.http.GET
import retrofit2.http.Header
import team.devlib.android.data.model.book.FetchMyBookmarksResponse
import team.devlib.android.data.util.RequestUrl

interface BookmarkApi {
    @GET(RequestUrl.Book.mark)
    suspend fun fetchMyBookmarks(
        @Header("Authorization") token: String,
    ): FetchMyBookmarksResponse
}
