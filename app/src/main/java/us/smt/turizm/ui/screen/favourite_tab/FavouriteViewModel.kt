package us.smt.turizm.ui.screen.favourite_tab

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import us.smt.turizm.data.database.remote.FireBaseHelper
import us.smt.turizm.domen.model.PlaceDetails
import us.smt.turizm.ui.screen.details.DetailsScreen
import us.smt.turizm.ui.utils.AppNavigator
import us.smt.turizm.ui.utils.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(navigator: AppNavigator) : BaseViewModel<FavouriteState, FavouriteIntent>(FavouriteState(), navigator) {

    override fun onAction(intent: FavouriteIntent) {
        when (intent) {
            is FavouriteIntent.Favourite -> favourite(intent.id)
            is FavouriteIntent.OpenDetails -> navigate(DetailsScreen(intent.id))
            FavouriteIntent.Init -> loadList()
        }
    }

    private var list = emptyList<PlaceDetails>()

    private fun loadList() {
        viewModelScope.launch {
            list = FireBaseHelper.getInstance().getAllData()
            list = list.filter {
                it.isFavourite
            }
            update(
                state = state.value.copy(
                    favourites = list,
                    isLoading = false
                )
            )
        }
    }

    private fun favourite(id: String) {
        viewModelScope.launch {
            val findData = list.find { it.id == id }
            list = list.filter {
                it.id != id
            }
            update(
                state = state.value.copy(favourites = list)
            )
            findData?.let {
                FireBaseHelper.getInstance().updatePlace(findData.copy(
                    isFavourite = false
                ))
            }
        }
    }

}