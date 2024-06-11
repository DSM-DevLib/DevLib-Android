package team.devlib.android.data.remote.api

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import team.devlib.android.data.remote.model.question.FetchQuestionDetailsResponse
import team.devlib.android.data.remote.model.question.FetchQuestionsResponse
import team.devlib.android.data.remote.model.question.PostQuestionRequest
import team.devlib.android.data.util.RequestUrl

interface QuestionApi {
    @GET(RequestUrl.Question.question)
    suspend fun fetchQuestions(
        @Query("title") title: String,
    ): FetchQuestionsResponse

    @GET(RequestUrl.Question.details)
    suspend fun fetchQuestionDetails(
        @Path("question-id") questionId: Long,
    ): FetchQuestionDetailsResponse

    @PATCH(RequestUrl.Question.details)
    suspend fun editQuestion(
        @Path("question-id") questionId: Long,
    )

    @DELETE(RequestUrl.Question.details)
    suspend fun deleteQuestion(
        @Path("question-id") questionId: Long,
    )

    @POST(RequestUrl.Question.question)
    suspend fun postQuestion(
        @Body postQuestionRequest: PostQuestionRequest,
    )

}