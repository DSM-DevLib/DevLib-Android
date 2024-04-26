package team.devlib.android.feature.mypage

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.devlib.android.base.BaseViewModel
import team.devlib.android.data.api.UserApi
import team.devlib.android.data.di.NetworkModule
import team.devlib.android.data.model.user.response.UserInformationResponse
import team.retum.network.util.RequestHandler
import javax.inject.Inject

@HiltViewModel
internal class MyPageViewModel @Inject constructor(
    private val userApi: UserApi,

) : BaseViewModel<MyPageState, MyPageSideEffect>(MyPageState()) {

    init {
        fetchUserInformation()
    }

    private fun fetchUserInformation() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                RequestHandler<UserInformationResponse>().request {
                    userApi.fetchUserInformation(NetworkModule.accessToken)
                }
            }.onSuccess {
                setState { state.value.copy(name = it.accountId) }
            }.onFailure {

            }
        }
    }

}

internal data class MyPageState(
    val profileImageUrl: String = "",
    val name: String = "",
)

internal sealed interface MyPageSideEffect {

}
