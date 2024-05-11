package team.devlib.android.data.local.storage

interface AuthDataStorage {
    fun setAccessToken(token: String?)
    fun fetchAccessToken(): String
    fun clearAccessToken()
}