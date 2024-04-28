package team.devlib.android.feature.bookdetails

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.devlib.android.base.BaseViewModel
import team.devlib.android.data.api.BookApi
import team.devlib.android.data.di.NetworkModule
import team.devlib.android.data.model.book.FetchBookDetailsResponse
import javax.inject.Inject

@HiltViewModel
internal class BookDetailsViewModel @Inject constructor(
    private val bookApi: BookApi,
) : BaseViewModel<BookDetailsState, BookDetailsSideEffect>(BookDetailsState()) {

    fun setId(id: String) = setState {
        state.value.copy(id = id)
    }

    fun fetchBookDetails() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                bookApi.fetchBookDetails(
                    token = NetworkModule.accessToken,
                    bookId = state.value.id.toLong(),
                )
            }.onSuccess {
                setState {
                    state.value.copy(details = it)
                }
            }.onFailure {

            }
        }
    }
}

internal data class BookDetailsState(
    val id: String = "",
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

}
