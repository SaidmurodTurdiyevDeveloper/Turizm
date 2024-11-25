package us.smt.turizm.ui.utils

import cafe.adriel.voyager.core.screen.Screen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

typealias NavigationArgs = cafe.adriel.voyager.navigator.Navigator.() -> Unit

class AppNavigator @Inject constructor() {
    companion object {
        private val _navigatorState = MutableSharedFlow<NavigationArgs>()
        val navigatorState = _navigatorState.asSharedFlow()
        private val _screens = MutableSharedFlow<List<Screen>>()
        val screens = _screens.asSharedFlow()
    }

    private suspend fun navigate(screen: NavigationArgs) {
        _navigatorState.emit(screen)
    }

    suspend fun navigateTo(screen: Screen) = navigate {
        push(screen)
    }

    suspend fun back() = navigate {
        pop()
    }

    suspend fun backAll() = navigate {
        popAll()
    }

    suspend fun replace(screen: Screen) = navigate {
        replace(screen)
    }

    suspend fun <T : Screen> backUntil(screen: Class<T>) = navigate {
        popUntil {
            screen.isInstance(it)
        }
    }

    suspend fun replaceAll(screen: Screen) = navigate {
        replaceAll(screen)
    }

    suspend fun screens() {
        val scope = CoroutineScope(Dispatchers.IO)
        navigate {
            scope.launch {
                _screens.emit(items)
            }
        }
    }

}