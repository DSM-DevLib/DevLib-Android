package team.devlib.android.data.remote.api

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import team.devlib.android.data.remote.model.question.CreateReplyRequest
import team.devlib.android.data.util.RequestUrl

interface ReplyApi {
    @POST(RequestUrl.Reply.reply)
    suspend fun postReply(
        @Path("question-id") questionId: Long,
        @Body createReplyRequest: CreateReplyRequest,
    )

    @DELETE(RequestUrl.Reply.deleteReply)
    suspend fun deleteReply(
        @Path("reply-id") replyId: Long,
    )

    @PATCH(RequestUrl.Reply.deleteReply)
    suspend fun editReply(
        @Path("reply-id") replyId: Long,
    )

    
}