package team.devlib.android

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<S, E>(initialState: S) : ViewModel() {
    private val _state: MutableStateFlow<S> = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    private val _sideEffect: MutableSharedFlow<E> = MutableSharedFlow()

    protected fun setState(newState: () -> S) {
        _state.value = newState()
    }

    protected fun postSideEffect(sideEffect: E) {
        viewModelScope.launch(Dispatchers.IO) {
            this@BaseViewModel._sideEffect.emit(sideEffect)
        }
    }

    internal fun collectSideEffect(block: suspend (E) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            _sideEffect.collect {
                block(it)
            }
        }
    }
}
