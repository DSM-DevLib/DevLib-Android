package team.devlib.android.feature.question

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.devlib.android.base.BaseViewModel
import team.devlib.android.data.remote.api.QuestionApi
import team.devlib.android.data.remote.model.question.FetchQuestionsResponse
import team.devlib.android.data.remote.model.question.PostQuestionRequest
import team.devlib.android.feature.home.Type
import javax.inject.Inject

@HiltViewModel
internal class QuestionViewModel @Inject constructor(
    private val questionApi: QuestionApi,
) : BaseViewModel<QuestionState, QuestionSideEffect>(QuestionState()) {

    internal val questions = mutableStateListOf<FetchQuestionsResponse.Question>()

    init {
        fetchQuestions()
    }

    private fun fetchQuestions() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                questionApi.fetchQuestions()
            }.onSuccess {
                questions.addAll(it.questions)
            }
        }
    }

    internal fun onKeywordChange(keyword: String) = setState {
        state.value.copy(keyword = keyword)
    }

    fun postQuestion() {
        viewModelScope.launch(Dispatchers.IO) {
            with(state.value) {
                runCatching {
                    questionApi.postQuestion(
                        postQuestionRequest = PostQuestionRequest(
                            title = title,
                            content = content,
                        )
                    )
                }.onSuccess {
                    postSideEffect(QuestionSideEffect.Success)
                }
            }
        }
    }

    internal fun setTitle(title: String) = setState {
        state.value.copy(title = title)
    }

    internal fun setContent(content: String) = setState {
        state.value.copy(content = content)
    }

}

internal data class QuestionState(
    val keyword: String = "",
    val type: Type = Type.VIEW,
    val title: String = "",
    val content: String = "",
)

internal sealed interface QuestionSideEffect {
    data object Success : QuestionSideEffect
}
