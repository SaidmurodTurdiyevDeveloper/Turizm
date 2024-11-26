package us.smt.turizm.ui.screen.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import us.smt.turizm.data.database.local.shared.LocalStorage
import us.smt.turizm.data.database.remote.FireBaseHelper
import us.smt.turizm.ui.screen.tab.MainTabScreen
import us.smt.turizm.ui.utils.AppNavigator
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val navigator: AppNavigator, private val localStorage: LocalStorage) : ViewModel() {
    fun start() {
        viewModelScope.launch(Dispatchers.IO) {

            delay(200)
            navigator.replaceAll(MainTabScreen())
        }
    }
}