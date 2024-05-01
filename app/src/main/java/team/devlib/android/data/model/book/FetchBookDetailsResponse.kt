package team.devlib.android.data.model.book

import com.google.gson.annotations.SerializedName

data class FetchBookDetailsResponse(
    val id: String,
    val name: String,
    val author: String,
    val cover: String,
    val description: String,
    val price: Int,
    @SerializedName("purchase_site") val purchaseSite: String,
    @SerializedName("purchase_url") val purchaseUrl: String,
    @SerializedName("is_marked") val isMarked: Boolean,
)
