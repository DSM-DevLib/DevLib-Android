package team.devlib.android.feature.question

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.devlib.android.base.BaseViewModel
import team.devlib.android.data.remote.api.QuestionApi
import team.devlib.android.data.remote.model.question.FetchQuestionDetailsResponse
import team.devlib.android.data.util.RequestHandler
import javax.inject.Inject

@HiltViewModel
internal class QuestionDetailsViewModel @Inject constructor(
    private val questionApi: QuestionApi,
) : BaseViewModel<QuestionDetailsState, QuestionDetailsSideEffect>(QuestionDetailsState()) {

    internal val replies: SnapshotStateList<FetchQuestionDetailsResponse.Reply> =
        mutableStateListOf()

    fun setId(id: Long) = setState {
        state.value.copy(id = id)
    }

    fun fetchQuestionDetails() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                RequestHandler<FetchQuestionDetailsResponse>().request {
                    questionApi.fetchQuestionDetails(questionId = state.value.id)
                }
            }.onSuccess {
                setState {
                    state.value.copy(details = it)
                }
            }.onFailure {

            }
        }
    }
}

internal data class QuestionDetailsState(
    val id: Long = 0L,
    val replies: FetchQuestionDetailsResponse.Reply = FetchQuestionDetailsResponse.Reply(
        createdDate = "",
        username = "",
        likeCount = 0,
        content = "",
        bookId = 0L,
    ),
    val details: FetchQuestionDetailsResponse = FetchQuestionDetailsResponse(
        title = "",
        content = "",
        author = "",
        replyList = listOf(replies),
    ),
)

internal sealed interface QuestionDetailsSideEffect {
    data object Success : QuestionDetailsSideEffect
}
