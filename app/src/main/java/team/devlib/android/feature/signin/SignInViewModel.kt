package team.devlib.android.feature.signin

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.devlib.android.BaseViewModel
import team.devlib.android.data.api.UserApi
import team.devlib.android.data.di.NetworkModule
import team.devlib.android.data.model.user.request.SignInRequest
import team.devlib.android.data.model.user.response.TokenResponse
import team.devlib.android.domain.util.NotFoundException
import team.devlib.android.domain.util.UnAuthorizedException
import team.retum.network.util.RequestHandler
import javax.inject.Inject

@HiltViewModel
internal class SignInViewModel @Inject constructor(
    private val userApi: UserApi,
) : BaseViewModel<Unit, SignInSideEffect>(Unit) {

    fun signIn(
        accountId: String,
        password: String,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                RequestHandler<TokenResponse>().request {
                    userApi.signIn(
                        signInRequest = SignInRequest(
                            accountId = accountId,
                            password = password,
                        )
                    )
                }
            }.onSuccess {
                NetworkModule.accessToken = it.accessToken
                postSideEffect(SignInSideEffect.Success)
            }.onFailure {
                postSideEffect(
                    SignInSideEffect.Failure(
                        notFoundUser = it is NotFoundException,
                        invalidPassword = it is UnAuthorizedException,
                        message = it.message,
                    )
                )
            }
        }
    }
}

internal sealed interface SignInSideEffect {
    data object Success : SignInSideEffect
    data class Failure(
        val notFoundUser: Boolean,
        val invalidPassword: Boolean,
        val message: String?,
    ) : SignInSideEffect
}
