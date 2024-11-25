package us.smt.turizm.ui.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cafe.adriel.voyager.core.screen.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseViewModel<State, Intent>(initializeData: State, private val appNavigator: AppNavigator) : ViewModel() {
    private var _state = MutableStateFlow(initializeData)
    val state = _state.asStateFlow()

    abstract fun onAction(intent: Intent)

    protected fun back() {
        viewModelScope.launch {
            appNavigator.back()
        }
    }

    protected fun update(state: State) {
        _state.update {
            state
        }
    }

    protected fun navigate(screen: Screen) {
        viewModelScope.launch {
            appNavigator.navigateTo(screen)
        }
    }
}