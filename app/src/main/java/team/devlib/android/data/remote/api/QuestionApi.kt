package team.devlib.android.data.remote.api

import retrofit2.http.GET
import retrofit2.http.Header
import team.devlib.android.data.remote.model.question.FetchQuestionsResponse
import team.devlib.android.data.util.RequestUrl

interface QuestionApi {
    @GET(RequestUrl.Question.question)
    suspend fun fetchQuestions(): FetchQuestionsResponse
}