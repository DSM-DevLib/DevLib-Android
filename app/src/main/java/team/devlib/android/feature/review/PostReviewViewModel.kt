package team.devlib.android.feature.review

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.devlib.android.base.BaseViewModel
import team.devlib.android.data.remote.api.ReviewApi
import team.devlib.android.data.remote.model.review.PostReviewRequest
import team.devlib.android.data.util.RequestHandler
import team.devlib.android.domain.util.NotFoundException
import javax.inject.Inject

@HiltViewModel
internal class PostReviewViewModel @Inject constructor(
    private val reviewApi: ReviewApi,
) : BaseViewModel<Unit, PostReviewSideEffect>(Unit) {

    fun postReview(
        bookId: Long,
        point: Int,
        content: String,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                RequestHandler<Unit>().request {
                    reviewApi.postReview(
                        bookId = bookId,
                        postReviewRequest = PostReviewRequest(
                            point = point,
                            content = content,
                        )
                    )
                }
            }.onSuccess {
                postSideEffect(PostReviewSideEffect.Success)
            }.onFailure {
                when (it) {
                    is NotFoundException -> {
                        postSideEffect(PostReviewSideEffect.Failure("존재하지 않는 도서입니다"))
                    }

                    else -> {
                        postSideEffect(PostReviewSideEffect.Failure(it.message ?: it.toString()))
                    }
                }
            }
        }
    }
}

internal sealed interface PostReviewSideEffect {
    data object Success : PostReviewSideEffect
    data class Failure(val message: String) : PostReviewSideEffect
}
