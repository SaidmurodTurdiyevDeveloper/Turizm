package us.smt.turizm.ui.screen.details

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import us.smt.turizm.data.database.local.shared.LocalStorage
import us.smt.turizm.domen.model.PlaceData
import us.smt.turizm.ui.utils.AppNavigator
import us.smt.turizm.ui.utils.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewmodel @Inject constructor(
    private val localStorage: LocalStorage,
    navigator: AppNavigator
) : BaseViewModel<PlaceData?, DetailsIntent>(null, navigator) {
    override fun onAction(intent: DetailsIntent) {
        when (intent) {
            DetailsIntent.Back -> back()
            DetailsIntent.FavoriteChange -> onChangeFavourite()
            is DetailsIntent.LoadData -> getData(intent.id)
        }
    }

    private val gson = Gson()
    private var list = emptyList<PlaceData>()

    private fun getData(id: String) {
        Log.d("TagTagTga","getData")
        val typeToken = object : TypeToken<List<PlaceData>>() {}.type
        list = gson.fromJson(localStorage.allData, typeToken)
        val data = list.find {
            it.id == id
        }
        update(
            state = data
        )
    }

    private fun onChangeFavourite() {
        update(
            state = state.value?.copy(
                isFavourite = state.value?.isFavourite?.not() ?: false
            )
        )
        list = list.map {
            if (it.id == state.value?.id) {
                state.value ?: it
            } else
                it
        }
        localStorage.allData=gson.toJson(list)
    }

}