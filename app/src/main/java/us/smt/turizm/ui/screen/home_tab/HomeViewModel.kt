package us.smt.turizm.ui.screen.home_tab

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import us.smt.turizm.data.database.remote.FireBaseHelper
import us.smt.turizm.domen.model.PlaceDetails
import us.smt.turizm.ui.screen.details.DetailsScreen
import us.smt.turizm.ui.screen.search.SearchScreen
import us.smt.turizm.ui.utils.AppNavigator
import us.smt.turizm.ui.utils.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(navigator: AppNavigator) : BaseViewModel<HomeState, HomeIntent>(HomeState(), navigator) {

    override fun onAction(intent: HomeIntent) {
        when (intent) {
            HomeIntent.Init -> initData()
            is HomeIntent.OpenDetails -> navigate(DetailsScreen(intent.id))
            HomeIntent.OpenSearch -> navigate(SearchScreen())
            is HomeIntent.ChangeFavourite -> favourite(intent.data)
            HomeIntent.Back -> back()
        }
    }


    init {
        initData()
    }

    private fun initData() {
        viewModelScope.launch(Dispatchers.IO) {
            val list = FireBaseHelper.getInstance().getAllData()
            val listPopular = list.filter {
                (it.rating ?: 0.0) > 4.8
            }
            val near = list.filter {
                it.distance < 2
            }
            update(
                state = state.value.copy(
                    popular = listPopular,
                    near = near,
                    isLoading = false
                )
            )
        }
    }

    private fun favourite(data: PlaceDetails) {
        val listPopular = state.value.popular.map {
            if (data.id == it.id) {
                it.copy(isFavourite = it.isFavourite.not())
            } else it
        }.toMutableList()
        val near = state.value.near.map {
            if (data.id == it.id) {
                it.copy(isFavourite = it.isFavourite.not())
            } else it
        }.toMutableList()
        update(
            state = state.value.copy(popular = listPopular, near = near)
        )
        viewModelScope.launch(Dispatchers.IO) {
            data.copy(
                isFavourite = !data.isFavourite
            ).let {
                FireBaseHelper.getInstance().updatePlace(it)
            }
        }
    }
}