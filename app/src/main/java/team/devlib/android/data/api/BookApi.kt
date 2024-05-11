package team.devlib.android.data.api

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import team.devlib.android.data.model.book.FetchBookDetailsResponse
import team.devlib.android.data.model.book.FetchBookRankingResponse
import team.devlib.android.data.model.book.FetchBookReviewsResponse
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

    @GET(RequestUrl.Book.book)
    suspend fun searchBook(
        @Header("Authorization") token: String,
        @Query("name") name: String,
    ): FetchBookRankingResponse

    @GET(RequestUrl.Book.details)
    suspend fun fetchBookDetails(
        @Header("Authorization") token: String,
        @Path("book-id") bookId: Long,
    ): FetchBookDetailsResponse

    @POST(RequestUrl.Book.bookmark)
    suspend fun bookmark(
        @Header("Authorization") token: String,
        @Path("book-id") bookId: Long,
    )

    @GET(RequestUrl.Book.review)
    suspend fun fetchBookReviews(
        @Header("Authorization") token: String,
        @Path("book-id") bookId: Long,
    ): FetchBookReviewsResponse

    @POST(RequestUrl.Book.review)
    suspend fun postReview(
        @Header("Authorization") token: String,
        @Path("book-id") bookId: Long,
    )
}
