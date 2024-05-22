package team.devlib.android.feature.splash

import dagger.hilt.android.lifecycle.HiltViewModel
import team.devlib.android.base.BaseViewModel
import team.devlib.android.data.local.storage.AuthDataStorage
import javax.inject.Inject

@HiltViewModel
internal class SplashViewModel @Inject constructor(
    private val authDataStorage: AuthDataStorage,
): BaseViewModel<Unit, SplashSideEffect>(Unit) {
    internal fun checkAccessToken() {
        runCatching {
            authDataStorage.fetchAccessToken()
        }.onSuccess {
            postSideEffect(SplashSideEffect.Success)
        }.onFailure {
            postSideEffect(SplashSideEffect.Failure)
        }
    }
}

internal interface SplashSideEffect {
    data object Success: SplashSideEffect
    data object Failure: SplashSideEffect
}
