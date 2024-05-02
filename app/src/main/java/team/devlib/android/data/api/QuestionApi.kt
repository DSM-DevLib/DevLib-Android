package team.devlib.android.data.api

import retrofit2.http.GET
import retrofit2.http.Header
import team.devlib.android.data.model.question.FetchQuestionsResponse
import team.devlib.android.data.util.RequestUrl

interface QuestionApi {
    @GET(RequestUrl.Question.question)
    suspend fun fetchQuestions(
        @Header("Authorization") token: String,
    ): FetchQuestionsResponse
}