package team.devlib.android.feature.signup

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.devlib.android.BaseViewModel
import team.devlib.android.data.api.UserApi
import team.devlib.android.data.di.NetworkModule
import team.devlib.android.data.model.user.request.SignUpRequest
import javax.inject.Inject

@HiltViewModel
internal class SignUpViewModel @Inject constructor(
    private val userApi: UserApi,
) : BaseViewModel<SignUpState, SignUpSideEffect>(SignUpState.getInitialState()) {

    fun signUp() {
        viewModelScope.launch(Dispatchers.IO) {
            with(state.value) {
                runCatching {
                    userApi.signUp(
                        signUpRequest = SignUpRequest(
                            accountId = accountId,
                            password = password,
                        )
                    )
                }.onSuccess {
                    NetworkModule.accessToken = it.accessToken
                    postSideEffect(SignUpSideEffect.Success("성공적으로 회원가입 되었습니다!"))
                }.onFailure {
                    when (it) {
                        is KotlinNullPointerException -> {
                            postSideEffect(SignUpSideEffect.Success("성공적으로 회원가입 되었습니다!"))
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
    data object Failure : SignUpSideEffect
}
