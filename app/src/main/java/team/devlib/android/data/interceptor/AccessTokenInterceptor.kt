package team.devlib.android.data.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import team.devlib.android.data.local.storage.AuthDataStorage
import team.devlib.android.data.util.RequestUrl
import javax.inject.Inject

class AccessTokenInterceptor @Inject constructor(
    private val authDataStorage: AuthDataStorage
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val path = request.url.encodedPath
        val ignorePath = listOf(RequestUrl.User.auth)
        if (ignorePath.contains(path)) return chain.proceed(request)
        if (path.contains(RequestUrl.User.user) && request.method == "POST") return chain.proceed(request)

        val accessToken = authDataStorage.fetchAccessToken()
        return chain.proceed(
            request.newBuilder().addHeader(
                "Authorization",
                "$accessToken"
            ).build()
        )
    }
}