package us.smt.turizm.ui.screen.favourite_tab

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import us.smt.turizm.data.database.local.shared.LocalStorage
import us.smt.turizm.domen.model.PlaceData
import us.smt.turizm.ui.screen.details.DetailsScreen
import us.smt.turizm.ui.utils.AppNavigator
import us.smt.turizm.ui.utils.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(navigator: AppNavigator, private val localStorage: LocalStorage) : BaseViewModel<FavouriteState, FavouriteIntent>(FavouriteState(), navigator) {

    override fun onAction(intent: FavouriteIntent) {
        when (intent) {
            is FavouriteIntent.Favourite -> favourite(intent.id)
            is FavouriteIntent.OpenDetails -> navigate(DetailsScreen(intent.id))
            FavouriteIntent.Init -> loadList()
        }
    }

    private val gson = Gson()
    private var allList = emptyList<PlaceData>()
    private var list = emptyList<PlaceData>()

    private fun loadList() {
        val typeToken = object : TypeToken<List<PlaceData>>() {}.type
        list = gson.fromJson(localStorage.allData, typeToken)
        allList = list.toMutableList()
        list = list.filter {
            it.isFavourite
        }
        update(
            state = state.value.copy(favourites = list)
        )
    }

    private fun favourite(id: String) {
        list = list.filter {
            it.id != id
        }
        update(
            state = state.value.copy(favourites = list)
        )
        allList = allList.map {
            if (id == it.id) {
                it.copy(isFavourite = it.isFavourite.not())
            } else
                it
        }
        localStorage.allData = gson.toJson(allList)
    }

}