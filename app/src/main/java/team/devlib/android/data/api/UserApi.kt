package team.devlib.android.data.api

import retrofit2.http.Body
import retrofit2.http.POST
import team.devlib.android.data.util.RequestUrl
import team.devlib.android.data.model.user.request.SignInRequest
import team.devlib.android.data.model.user.request.SignUpRequest
import team.devlib.android.data.model.user.response.TokenResponse

interface UserApi {
    @POST(RequestUrl.User.auth)
    suspend fun signIn(
        @Body signInRequest: SignInRequest,
    ): TokenResponse

    @POST(RequestUrl.User.user)
    suspend fun signUp(
        @Body signUpRequest: SignUpRequest,
    ): TokenResponse
}
