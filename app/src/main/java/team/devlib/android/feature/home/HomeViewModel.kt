package team.devlib.android.feature.home

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.devlib.android.base.BaseViewModel
import team.devlib.android.data.remote.api.BookApi
import team.devlib.android.data.di.NetworkModule
import team.devlib.android.data.remote.model.book.FetchBookRankingResponse
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val bookApi: BookApi,
) : BaseViewModel<HomeState, HomeSideEffect>(HomeState()) {

    internal val books = mutableStateListOf<FetchBookRankingResponse.Book>()

    init {
        fetchBookRanking()
    }

    internal fun onKeywordChange(keyword: String) = setState {
        state.value.copy(keyword = keyword)
    }

    internal fun onTypeChange(type: Type) {
        setState {
            state.value.copy(type = type)
        }
        books.clear()
        fetchBookRanking()
    }

    private fun fetchBookRanking() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                bookApi.fetchBookRanking(type = state.value.type)
            }.onSuccess {
                books.addAll(it.books)
            }
        }
    }

    internal fun searchBook() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                bookApi.searchBook(name = state.value.keyword)
            }.onSuccess {
                books.clear()
                books.addAll(it.books)
            }
        }
    }

}

internal data class HomeState(
    val keyword: String = "",
    val type: Type = Type.VIEW,
)

internal sealed interface HomeSideEffect {

}
