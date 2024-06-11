package team.devlib.android.data.remote.api

import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path
import team.devlib.android.data.remote.model.review.PostReviewRequest
import team.devlib.android.data.util.RequestUrl

interface ReviewApi {
    @POST(RequestUrl.Review.POST_REVIEW)
    suspend fun postReview(
        @Path("book-id") bookId: Long,
        @Body postReviewRequest: PostReviewRequest,
    )
}
