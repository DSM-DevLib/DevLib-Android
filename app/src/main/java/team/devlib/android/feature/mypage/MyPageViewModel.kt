package team.devlib.android.feature.mypage

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.devlib.android.base.BaseViewModel
import team.devlib.android.data.remote.api.BookApi
import team.devlib.android.data.remote.api.UserApi
import team.devlib.android.data.di.NetworkModule
import team.devlib.android.data.remote.model.book.FetchMyBookmarksResponse
import team.devlib.android.data.remote.model.user.response.UserInformationResponse
import team.devlib.android.data.util.RequestHandler
import javax.inject.Inject

@HiltViewModel
internal class MyPageViewModel @Inject constructor(
    private val userApi: UserApi,
    private val bookApi: BookApi,
) : BaseViewModel<MyPageState, MyPageSideEffect>(MyPageState()) {

    val bookmarks: SnapshotStateList<FetchMyBookmarksResponse.Bookmark> = mutableStateListOf()

    init {
        fetchUserInformation()
        fetchMyBookmarks()
    }

    private fun fetchUserInformation() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                RequestHandler<UserInformationResponse>().request {
                    userApi.fetchUserInformation()
                }
            }.onSuccess {
                setState { state.value.copy(name = it.accountId) }
            }.onFailure {
                it.printStackTrace()
            }
        }
    }

    private fun fetchMyBookmarks() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                RequestHandler<FetchMyBookmarksResponse>().request {
                    bookApi.fetchMyBookmarks()
                }
            }.onSuccess {
                bookmarks.addAll(it.bookmarks)
            }.onFailure {
                it.printStackTrace()
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
