package us.smt.turizm.ui.screen.details

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import us.smt.turizm.data.database.remote.FireBaseHelper
import us.smt.turizm.domen.model.PlaceDetails
import us.smt.turizm.ui.utils.AppNavigator
import us.smt.turizm.ui.utils.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewmodel @Inject constructor(
    navigator: AppNavigator
) : BaseViewModel<PlaceDetails?, DetailsIntent>(null, navigator) {
    override fun onAction(intent: DetailsIntent) {
        when (intent) {
            DetailsIntent.Back -> back()
            DetailsIntent.FavoriteChange -> onChangeFavourite()
            is DetailsIntent.LoadData -> getData(intent.id)
        }
    }


    private fun getData(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val data = FireBaseHelper.getInstance().getPlaceById(id)
            update(
                state = data
            )
        }
    }

    private fun onChangeFavourite() {
        update(
            state = state.value?.copy(
                isFavourite = state.value?.isFavourite?.not() ?: false
            )
        )
        viewModelScope.launch(Dispatchers.IO) {
            state.value?.let {
                FireBaseHelper.getInstance().updatePlace(it)
            }
        }
    }

}