package team.devlib.android.feature.question

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.devlib.android.base.BaseViewModel
import team.devlib.android.data.api.QuestionApi
import team.devlib.android.data.di.NetworkModule
import team.devlib.android.data.model.question.FetchQuestionsResponse
import team.devlib.android.feature.home.Type
import javax.inject.Inject

@HiltViewModel
internal class QuestionViewModel @Inject constructor(
    private val questionApi: QuestionApi,
): BaseViewModel<QuestionState, QuestionSideEffect>(QuestionState()){

    internal val questions = mutableStateListOf<FetchQuestionsResponse.Question>()

    init {
        fetchQuestions()
    }

    private fun fetchQuestions() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                questionApi.fetchQuestions(
                    token = NetworkModule.accessToken,
                )
            }.onSuccess {
                questions.addAll(it.questions)
            }
        }
    }

    internal fun onKeywordChange(keyword: String) = setState {
        state.value.copy(keyword = keyword)
    }
}

internal data class QuestionState(
    val keyword: String = "",
    val type: Type = Type.VIEW,
)

internal sealed interface QuestionSideEffect {

}
