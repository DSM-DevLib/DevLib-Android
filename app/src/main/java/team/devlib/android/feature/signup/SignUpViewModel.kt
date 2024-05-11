package team.devlib.android.feature.signup

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.devlib.android.base.BaseViewModel
import team.devlib.android.data.remote.api.UserApi
import team.devlib.android.data.di.NetworkModule
import team.devlib.android.data.local.storage.AuthDataStorage
import team.devlib.android.data.remote.model.user.request.SignUpRequest
import team.devlib.android.data.remote.model.user.response.TokenResponse
import team.devlib.android.domain.util.ConflictException
import team.devlib.android.data.util.RequestHandler
import javax.inject.Inject

@HiltViewModel
internal class SignUpViewModel @Inject constructor(
    private val userApi: UserApi,
    private val authDataStorage: AuthDataStorage
) : BaseViewModel<SignUpState, SignUpSideEffect>(SignUpState.getInitialState()) {

    fun signUp() {
        viewModelScope.launch(Dispatchers.IO) {
            with(state.value) {
                runCatching {
                    RequestHandler<TokenResponse>().request {
                        userApi.signUp(
                            signUpRequest = SignUpRequest(
                                accountId = accountId,
                                password = password,
                            )
                        )
                    }
                }.onSuccess {
                    authDataStorage.setAccessToken(it.accessToken)
                    postSideEffect(SignUpSideEffect.Success("성공적으로 회원가입 되었습니다!"))
                }.onFailure {
                    when (it) {
                        is KotlinNullPointerException -> {
                            postSideEffect(SignUpSideEffect.Success("성공적으로 회원가입 되었습니다!"))
                        }

                        is ConflictException -> {
                            postSideEffect(SignUpSideEffect.Failure("이미 존재하는 이메일이에요"))
                        }
                    }
                }
            }
        }
    }

    internal fun setAccountId(accountId: String) = setState {
        state.value.copy(accountId = accountId)
    }

    internal fun setPassword(password: String) = setState {
        state.value.copy(password = password)
    }

    internal fun setRepeatPassword(repeatPassword: String) = setState {
        state.value.copy(repeatPassword = repeatPassword)
    }
}

internal data class SignUpState(
    val accountId: String,
    val password: String,
    val repeatPassword: String,
    val emailError: Boolean,
    val passwordError: Boolean,
    val repeatPasswordError: Boolean,
) {
    companion object {
        fun getInitialState() = SignUpState(
            accountId = "",
            password = "",
            repeatPassword = "",
            emailError = false,
            passwordError = false,
            repeatPasswordError = false,
        )
    }
}

internal sealed interface SignUpSideEffect {
    data class Success(val message: String) : SignUpSideEffect
    data class Failure(val message: String) : SignUpSideEffect
}
