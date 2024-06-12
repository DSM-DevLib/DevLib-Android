package team.devlib.android.feature.home

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.devlib.android.base.BaseViewModel
import team.devlib.android.data.remote.api.BookApi
import team.devlib.android.data.remote.api.ReplyApi
import team.devlib.android.data.remote.model.book.FetchBookRankingResponse
import team.devlib.android.data.remote.model.question.CreateReplyRequest
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val bookApi: BookApi,
    private val replyApi: ReplyApi,
) : BaseViewModel<HomeState, HomeSideEffect>(HomeState()) {

    internal val books = mutableStateListOf<FetchBookRankingResponse.Book>()
    var book = FetchBookRankingResponse.Book(
        rank = 0,
        id = 0L,
        name = "",
        author = "",
        cover = "",
        score = 0f,
        reviewCount = 0,
    )

    fun setId(id: Long) = setState {
        state.value.copy(questionId = id)
    }

    internal fun setBookId(bookId: Long) {
        setState {
            state.value.copy(bookId = bookId)
        }
    }

    internal fun onContentChange(content: String) = setState {
        state.value.copy(content = content)
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

    internal fun fetchBookRanking() {
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

    internal fun postReply() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                replyApi.postReply(
                    questionId = state.value.questionId,
                    createReplyRequest = CreateReplyRequest(
                        content = state.value.content,
                        bookId = state.value.bookId,
                    )
                )
            }.onSuccess {
                postSideEffect(HomeSideEffect.CreateReplySuccess)
            }
        }
    }
}

internal data class HomeState(
    val keyword: String = "",
    val type: Type = Type.VIEW,
    val bookId: Long = 0L,
    val questionId: Long = 0L,
    val content: String = "",
)

internal sealed interface HomeSideEffect {
    data object CreateReplySuccess : HomeSideEffect
}
