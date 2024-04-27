package team.devlib.android.data.api

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import team.devlib.android.data.model.book.FetchBookRankingResponse
import team.devlib.android.data.model.book.FetchMyBookmarksResponse
import team.devlib.android.data.util.RequestUrl
import team.devlib.android.feature.home.Type

interface BookApi {
    @GET(RequestUrl.Book.mark)
    suspend fun fetchMyBookmarks(
        @Header("Authorization") token: String,
    ): FetchMyBookmarksResponse

    @GET(RequestUrl.Book.rank)
    suspend fun fetchBookRanking(
        @Header("Authorization") token: String,
        @Query("type") type: Type,
    ): FetchBookRankingResponse
}
