package team.devlib.android.feature.bookdetails

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.devlib.android.base.BaseViewModel
import team.devlib.android.data.api.BookApi
import team.devlib.android.data.di.NetworkModule
import team.devlib.android.data.model.book.FetchBookDetailsResponse
import team.devlib.android.data.model.book.FetchBookReviewsResponse
import team.retum.network.util.RequestHandler
import javax.inject.Inject

@HiltViewModel
internal class BookDetailsViewModel @Inject constructor(
    private val bookApi: BookApi,
) : BaseViewModel<BookDetailsState, BookDetailsSideEffect>(BookDetailsState()) {

    internal val reviews: SnapshotStateList<FetchBookReviewsResponse.Review> = mutableStateListOf()

    fun setId(id: Long) = setState {
        state.value.copy(id = id)
    }

    fun fetchBookDetails() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                RequestHandler<FetchBookDetailsResponse>().request {
                    bookApi.fetchBookDetails(
                        token = NetworkModule.accessToken,
                        bookId = state.value.id.toLong(),
                    )
                }
            }.onSuccess {
                setState {
                    state.value.copy(details = it)
                }
            }.onFailure {

            }
        }
    }

    fun bookmark() {
        setState {
            state.value.copy(
                details = state.value.details.copy(
                    isMarked = !state.value.details.isMarked
                )
            )
        }
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                RequestHandler<Unit>().request {
                    bookApi.bookmark(
                        token = NetworkModule.accessToken,
                        bookId = state.value.id,
                    )
                }
            }
        }
    }

    fun fetchBookReviews() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                RequestHandler<FetchBookReviewsResponse>().request {
                    bookApi.fetchBookReviews(
                        token = NetworkModule.accessToken,
                        bookId = state.value.id,
                    )
                }
            }.onSuccess {
                reviews.clear()
                reviews.addAll(it.reviews)
            }
        }
    }

    fun postReview() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                RequestHandler<Unit>().request {
                    bookApi.postReview(
                        token = NetworkModule.accessToken,
                        bookId = state.value.id,
                    )
                }
            }.onSuccess {
                postSideEffect(BookDetailsSideEffect.Success)
            }.onFailure {

            }
        }
    }
}

internal data class BookDetailsState(
    val id: Long = 0L,
    val details: FetchBookDetailsResponse = FetchBookDetailsResponse(
        id = "",
        name = "",
        author = "",
        cover = "",
        description = "",
        price = 0,
        purchaseSite = "",
        purchaseUrl = "",
        isMarked = false,
    )
)

internal sealed interface BookDetailsSideEffect {
    data object Success : BookDetailsSideEffect
}
