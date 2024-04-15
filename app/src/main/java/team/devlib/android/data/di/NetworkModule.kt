package team.devlib.android.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import team.devlib.android.data.api.UserApi
import javax.inject.Singleton

private const val BASE_URL = "http://localhost:8080"

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    internal var accessToken = ""

    private fun getLoggingInterceptor() =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    @Singleton
    fun provideRetrofit(
        gsonConverterFactory: GsonConverterFactory,
        okHttpClient: OkHttpClient,
    ): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(gsonConverterFactory)
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .build()
    }

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(/*tokenInterceptor: Interceptor*/): OkHttpClient {
        return OkHttpClient.Builder()
            //.addInterceptor(tokenInterceptor)
            .addInterceptor(getLoggingInterceptor())
            .build()
    }

    /*@Provides
    @Singleton
    fun provideTokenInterceptor(localUserDataSource: LocalUserDataSource): Interceptor {
        return TokenInterceptor(localUserDataSource = localUserDataSource)
    }*/

    @Provides
    @Singleton
    fun provideUserApi(retrofit: Retrofit): UserApi {
        return retrofit.create(UserApi::class.java)
    }
}
