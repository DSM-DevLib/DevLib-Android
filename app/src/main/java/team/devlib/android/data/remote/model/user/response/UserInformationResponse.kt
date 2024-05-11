package team.devlib.android.data.remote.model.user.response

import com.google.gson.annotations.SerializedName

data class UserInformationResponse(
    @SerializedName("account_id") val accountId: String,
)
