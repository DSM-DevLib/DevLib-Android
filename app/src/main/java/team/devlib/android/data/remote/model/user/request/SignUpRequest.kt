package team.devlib.android.data.remote.model.user.request

import com.google.gson.annotations.SerializedName

data class SignUpRequest(
    @SerializedName("account_id") val accountId: String,
    @SerializedName("password") val password: String,
)
