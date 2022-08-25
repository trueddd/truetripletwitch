package com.github.trueddd.truetripletwitch.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class StatefulViewModel<S> : ViewModel() {

    protected abstract fun initialState(): S

    private val _stateFlow by lazy { MutableStateFlow(initialState()) }
    val stateFlow: StateFlow<S>
        get() = _stateFlow

    fun updateState(modifyBlock: (S) -> S) {
        _stateFlow.value = modifyBlock(_stateFlow.value)
    }
}