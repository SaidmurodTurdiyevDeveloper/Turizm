package us.smt.turizm.ui.screen.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import us.smt.turizm.data.database.local.shared.LocalStorage
import us.smt.turizm.domen.model.getAllData
import us.smt.turizm.ui.screen.tab.MainTabScreen
import us.smt.turizm.ui.utils.AppNavigator
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val navigator: AppNavigator, private val localStorage: LocalStorage) : ViewModel() {
    fun start() {
        viewModelScope.launch {
            localStorage.allData.ifBlank {
                val ls = getAllData().shuffled()
                val gson = Gson()
                localStorage.allData = gson.toJson(ls)
            }
            delay(100)
            navigator.replace(MainTabScreen())
        }
    }
}