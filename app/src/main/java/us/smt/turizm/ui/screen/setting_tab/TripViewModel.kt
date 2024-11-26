package us.smt.turizm.ui.screen.setting_tab

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import us.smt.turizm.data.database.remote.FireBaseHelper
import us.smt.turizm.ui.screen.details.DetailsScreen
import us.smt.turizm.ui.utils.AppNavigator
import us.smt.turizm.ui.utils.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class TripViewModel @Inject constructor(navigator: AppNavigator) : BaseViewModel<TripState, TripIntent>(TripState(), navigator) {

    override fun onAction(intent: TripIntent) {
        when (intent) {
            TripIntent.Init -> loadList()
            is TripIntent.OpenItem -> navigate(DetailsScreen(intent.id))
        }
    }


    private fun loadList() {
        viewModelScope.launch {
            val list = FireBaseHelper.getInstance().getAllData()
            update(
                state = state.value.copy(tripList = list, isLoading = false)
            )
        }

    }

}