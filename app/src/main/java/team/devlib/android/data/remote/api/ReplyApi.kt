package team.devlib.android.data.remote.api

import retrofit2.http.POST
import retrofit2.http.Path
import team.devlib.android.data.util.RequestUrl

interface ReplyApi {
    @POST(RequestUrl.Reply.reply)
    suspend fun postReply(
        @Path("question-id") questionId: Long,
    )
}