package team.devlib.android.data.api

import retrofit2.http.Body
import retrofit2.http.POST
import team.devlib.android.data.util.RequestUrl
import team.devlib.android.data.model.user.request.SignInRequest
import team.devlib.android.data.model.user.response.SignInResponse

interface UserApi {
    @POST(RequestUrl.User.auth)
    suspend fun signIn(
        @Body signInRequest: SignInRequest,
    ): SignInResponse
}
