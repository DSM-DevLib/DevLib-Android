package team.devlib.android.feature.question

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.devlib.android.base.BaseViewModel
import team.devlib.android.data.remote.api.QuestionApi
import team.devlib.android.data.remote.api.ReplyApi
import team.devlib.android.data.remote.model.question.FetchQuestionDetailsResponse
import team.devlib.android.data.util.RequestHandler
import javax.inject.Inject

@HiltViewModel
internal class QuestionDetailsViewModel @Inject constructor(
    private val questionApi: QuestionApi,
    private val replyApi: ReplyApi,
) : BaseViewModel<QuestionDetailsState, QuestionDetailsSideEffect>(QuestionDetailsState()) {

    internal val replies = mutableStateListOf<FetchQuestionDetailsResponse.Reply>()

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
                setState { state.value.copy(details = it) }
                replies.clear()
                replies.addAll(it.replyList)
            }.onFailure {

            }
        }
    }

    fun postGood(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                RequestHandler<Unit>().request {
                    replyApi.postGood(replyId = id)
                }
            }
        }
    }

    fun deleteReply(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                replyApi.deleteReply(replyId = id)
            }.onSuccess {
                val reply = replies.find { it.id == id }
                replies.remove(reply)
            }
        }
    }

    fun deleteGood(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                RequestHandler<Unit>().request {
                    replyApi.deleteGood(replyId = id)
                }
            }
        }
    }

    fun deleteQuestion() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                questionApi.deleteQuestion(questionId = state.value.id)
            }.onSuccess {
                postSideEffect(QuestionDetailsSideEffect.DeleteSuccess)
            }
        }
    }
}

internal data class QuestionDetailsState(
    val id: Long = 0L,
    val details: FetchQuestionDetailsResponse = FetchQuestionDetailsResponse(
        id = 0L,
        title = "",
        content = "",
        author = "",
        mine = false,
        replyList = emptyList(),
    ),
)

internal sealed interface QuestionDetailsSideEffect {
    data object DeleteSuccess : QuestionDetailsSideEffect
}
