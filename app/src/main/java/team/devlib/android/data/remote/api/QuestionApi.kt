package team.devlib.android.data.remote.api

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import team.devlib.android.data.remote.model.question.FetchQuestionDetailsResponse
import team.devlib.android.data.remote.model.question.FetchQuestionsResponse
import team.devlib.android.data.util.RequestUrl

interface QuestionApi {
    @GET(RequestUrl.Question.question)
    suspend fun fetchQuestions(): FetchQuestionsResponse

    @GET(RequestUrl.Question.details)
    suspend fun fetchQuestionDetails(
        @Path("question-id") questionId: Long,
    ): FetchQuestionDetailsResponse
}