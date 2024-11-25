package us.smt.turizm.ui.screen.home_tab

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import us.smt.turizm.data.database.local.shared.LocalStorage
import us.smt.turizm.domen.model.PlaceData
import us.smt.turizm.ui.screen.details.DetailsScreen
import us.smt.turizm.ui.screen.search.SearchScreen
import us.smt.turizm.ui.utils.AppNavigator
import us.smt.turizm.ui.utils.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(navigator: AppNavigator, private val localStorage: LocalStorage) : BaseViewModel<HomeState, HomeIntent>(HomeState(), navigator) {

    override fun onAction(intent: HomeIntent) {
        when (intent) {
            HomeIntent.Init -> initData()
            is HomeIntent.OpenDetails -> navigate(DetailsScreen(intent.id))
            HomeIntent.OpenSearch -> navigate(SearchScreen())
            is HomeIntent.ChangeFavourite -> favourite(intent.id)
            HomeIntent.Back -> back()
        }
    }

    private val gson = Gson()
    private var list = emptyList<PlaceData>()

    init {
        initData()
    }

    private fun initData() {
        val typeToken = object : TypeToken<List<PlaceData>>() {}.type
        list = gson.fromJson(localStorage.allData, typeToken)
        val listPopular = list.filter {
            (it.rating ?: 0.0) > 4.8
        }
        val near = list.filter {
            it.distance < 2
        }
        update(
            state = state.value.copy(popular = listPopular, near = near)
        )
    }

    private fun favourite(id: String) {
        list = list.map {
            if (id == it.id) {
                it.copy(isFavourite = it.isFavourite.not())
            } else
                it
        }
        localStorage.allData = gson.toJson(list)

        val listPopular = list.filter {
            (it.rating ?: 0.0) > 4.0
        }.toMutableList()
        val near = list.filter {
            it.distance < 10
        }.toMutableList()
        update(
            state = state.value.copy(popular = listPopular, near = near)
        )
    }
}