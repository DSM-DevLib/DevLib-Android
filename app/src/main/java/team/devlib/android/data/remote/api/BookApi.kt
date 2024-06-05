package team.devlib.android.data.remote.api

import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import team.devlib.android.data.model.book.FetchBookDetailsResponse
import team.devlib.android.data.model.book.FetchBookReviewsResponse
import team.devlib.android.data.remote.model.book.FetchBookRankingResponse
import team.devlib.android.data.remote.model.book.FetchMyBookmarksResponse
import team.devlib.android.data.util.RequestUrl
import team.devlib.android.feature.home.Type

interface BookApi {
    @GET(RequestUrl.Book.mark)
    suspend fun fetchMyBookmarks(): FetchMyBookmarksResponse

    @GET(RequestUrl.Book.rank)
    suspend fun fetchBookRanking(
        @Query("type") type: Type
    ): FetchBookRankingResponse

    @GET(RequestUrl.Book.book)
    suspend fun searchBook(
        @Query("name") name: String
    ): FetchBookRankingResponse

    @GET(RequestUrl.Book.details)
    suspend fun fetchBookDetails(
        @Path("book-id") bookId: Long,
    ): FetchBookDetailsResponse

    @POST(RequestUrl.Book.bookmark)
    suspend fun bookmark(
        @Path("book-id") bookId: Long,
    )

    @GET(RequestUrl.Review.)
    suspend fun fetchBookReviews(
        @Path("book-id") bookId: Long,
    ): FetchBookReviewsResponse
}
