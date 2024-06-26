package team.devlib.android.feature.bookdetails

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.devlib.android.base.BaseViewModel
import team.devlib.android.data.model.book.FetchBookDetailsResponse
import team.devlib.android.data.model.book.FetchBookReviewsResponse
import team.devlib.android.data.remote.api.BookApi
import team.devlib.android.data.util.RequestHandler
import javax.inject.Inject

@HiltViewModel
internal class BookDetailsViewModel @Inject constructor(
    private val bookApi: BookApi,
) : BaseViewModel<BookDetailsState, Unit>(BookDetailsState()) {

    internal val reviews: SnapshotStateList<FetchBookReviewsResponse.Review> = mutableStateListOf()

    fun setId(id: Long) = setState {
        state.value.copy(id = id)
    }

    fun fetchBookDetails() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                RequestHandler<FetchBookDetailsResponse>().request {
                    bookApi.fetchBookDetails(bookId = state.value.id)
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
                    bookApi.bookmark(bookId = state.value.id)
                }
            }
        }
    }

    fun fetchBookReviews() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                RequestHandler<FetchBookReviewsResponse>().request {
                    bookApi.fetchBookReviews(bookId = state.value.id)
                }
            }.onSuccess {
                reviews.clear()
                reviews.addAll(it.reviews)
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
