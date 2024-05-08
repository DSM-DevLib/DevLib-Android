package team.devlib.android.data.remote.model.user.response

import com.google.gson.annotations.SerializedName

data class TokenResponse(
    @SerializedName("access_token") val accessToken: String,
)
