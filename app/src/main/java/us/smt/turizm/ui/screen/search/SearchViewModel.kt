package us.smt.turizm.ui.screen.search

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import us.smt.turizm.data.database.remote.FireBaseHelper
import us.smt.turizm.domen.model.PlaceDetails
import us.smt.turizm.ui.screen.details.DetailsScreen
import us.smt.turizm.ui.utils.AppNavigator
import us.smt.turizm.ui.utils.BaseViewModel
import us.smt.turizm.ui.utils.TextFieldData
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(navigator: AppNavigator) : BaseViewModel<SearchState, SearchIntent>(SearchState(), navigator) {

    override fun onAction(intent: SearchIntent) {
        when (intent) {
            is SearchIntent.OpenDetails -> navigate(DetailsScreen(intent.id))
            is SearchIntent.ChangeFavourite -> favourite(intent.id)
            is SearchIntent.Search -> onChangeText(intent.text)
            SearchIntent.Back -> back()
            SearchIntent.Init -> loadList()
        }
    }

    private var list = emptyList<PlaceDetails>()

    private fun onChangeText(text: String) {
        if (text.isBlank()) {
            update(
                state = state.value.copy(
                    search = TextFieldData(
                        text = text
                    ), list = list,
                    isLoading = false
                )
            )
        } else {
            update(
                state = state.value.copy(
                    isLoading = true,
                    search = TextFieldData(
                        text = text
                    )
                )
            )
            viewModelScope.launch {

                val exist = mutableListOf<PlaceDetails>()
                val aboutLs = mutableListOf<PlaceDetails>()
                val place = mutableListOf<PlaceDetails>()
                val notExistAboutLs = mutableListOf<PlaceDetails>()

                for (it in list) {
                    val isNameMatch = it.name.contains(text, true)
                    val isAboutMatch = it.about.contains(text, false)
                    val isAddressMatch = it.address.contains(text, false)

                    if (isNameMatch) {
                        exist.add(it)
                    } else {
                        if (isAboutMatch) {
                            aboutLs.add(it)
                        } else if (isAddressMatch) {
                            place.add(it)
                        } else {
                            notExistAboutLs.add(it)
                        }
                    }
                }

                val all = ArrayList(exist)
                all.addAll(aboutLs)
                all.addAll(place)
                update(
                    state = state.value.copy(list = all, isLoading = false)
                )
            }

        }
    }

    private fun loadList() {
        viewModelScope.launch {
            list = FireBaseHelper.getInstance().getAllData()
            update(
                state = state.value.copy(list = list, isLoading = false)
            )
        }

    }

    private fun favourite(id: String) {
        val current = state.value.list.map {
            if (id == it.id) {
                it.copy(isFavourite = it.isFavourite.not())
            } else it
        }.toMutableList()
        update(
            state = state.value.copy(list = current)
        )
        viewModelScope.launch(Dispatchers.IO) {
            val findData = list.find { it.id == id }
            list = list.filter {
                it.id != id
            }
            findData?.let {
                FireBaseHelper.getInstance().updatePlace(findData.copy(
                    isFavourite = findData.isFavourite.not()
                ))
            }
        }
    }
}