package team.devlib.android.feature.review

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.devlib.android.base.BaseViewModel
import team.devlib.android.data.remote.api.BookApi
import team.devlib.android.data.util.RequestHandler
import javax.inject.Inject

@HiltViewModel
internal class PostReviewViewModel @Inject constructor(
    private val bookApi: BookApi,
) : BaseViewModel<Unit, PostReviewSideEffect>(Unit) {

    fun postReview(
        review: String,
        bookId: Long,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                RequestHandler<Unit>().request {
                    bookApi.postReview(bookId = bookId)
                }
            }.onSuccess {
                postSideEffect(PostReviewSideEffect.Success)
            }.onFailure {
                postSideEffect(PostReviewSideEffect.Failure(it.message ?: it.toString()))
            }
        }
    }
}

internal sealed interface PostReviewSideEffect {
    data object Success : PostReviewSideEffect
    data class Failure(val message: String) : PostReviewSideEffect
}
