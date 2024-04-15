package team.devlib.android.data.model.user.response

import com.google.gson.annotations.SerializedName

data class SignInResponse(
    @SerializedName("access_token") val accessToken: String,
)
