package team.devlib.android.data.remote.api

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import team.devlib.android.data.remote.model.user.request.SignInRequest
import team.devlib.android.data.remote.model.user.request.SignUpRequest
import team.devlib.android.data.remote.model.user.response.TokenResponse
import team.devlib.android.data.remote.model.user.response.UserInformationResponse
import team.devlib.android.data.util.RequestUrl

interface UserApi {
    @POST(RequestUrl.User.auth)
    suspend fun signIn(
        @Body signInRequest: SignInRequest
    ): TokenResponse

    @POST(RequestUrl.User.user)
    suspend fun signUp(
        @Body signUpRequest: SignUpRequest
    ): TokenResponse

    @GET(RequestUrl.User.user)
    suspend fun fetchUserInformation(): UserInformationResponse
}
