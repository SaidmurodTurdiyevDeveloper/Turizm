package us.smt.turizm.ui.screen.search

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import us.smt.turizm.data.database.local.shared.LocalStorage
import us.smt.turizm.domen.model.PlaceData
import us.smt.turizm.ui.screen.details.DetailsScreen
import us.smt.turizm.ui.utils.AppNavigator
import us.smt.turizm.ui.utils.BaseViewModel
import us.smt.turizm.ui.utils.TextFieldData
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(navigator: AppNavigator, private val localStorage: LocalStorage) : BaseViewModel<SearchState, SearchIntent>(SearchState(), navigator) {

    override fun onAction(intent: SearchIntent) {
        when (intent) {
            is SearchIntent.OpenDetails -> navigate(DetailsScreen(intent.id))
            is SearchIntent.ChangeFavourite -> favourite(intent.id)
            is SearchIntent.Search -> onChangeText(intent.text)
            SearchIntent.Back -> back()
            SearchIntent.Init -> loadList()
        }
    }

    private val gson = Gson()
    private var list = emptyList<PlaceData>()

    private fun onChangeText(text: String) {
        if (text.isBlank()) {
            update(
                state = state.value.copy(
                    search = TextFieldData(
                        text = text
                    ), list = list
                )
            )
        } else {
            val exist = mutableListOf<PlaceData>()
            val aboutLs = mutableListOf<PlaceData>()
            val place = mutableListOf<PlaceData>()
            val notExistAboutLs = mutableListOf<PlaceData>()

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
                state = state.value.copy(search = TextFieldData(text = text), list = all)
            )

        }
    }

    private fun loadList() {
        val typeToken = object : TypeToken<List<PlaceData>>() {}.type
        list = gson.fromJson(localStorage.allData, typeToken)
        update(
            state = state.value.copy(list = list)
        )

    }

    private fun favourite(id: String) {
        list = list.map {
            if (id == it.id) {
                it.copy(isFavourite = it.isFavourite.not())
            } else it
        }
        localStorage.allData = gson.toJson(list)
        val current = state.value.list.map {
            if (id == it.id) {
                it.copy(isFavourite = it.isFavourite.not())
            } else it
        }.toMutableList()
        update(
            state = state.value.copy(list = current)
        )
    }
}