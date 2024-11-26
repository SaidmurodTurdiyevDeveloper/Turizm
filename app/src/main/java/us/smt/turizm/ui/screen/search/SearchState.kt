package us.smt.turizm.ui.screen.search

import us.smt.turizm.domen.model.PlaceDetails
import us.smt.turizm.ui.utils.TextFieldData

data class SearchState(
    val isLoading:Boolean=true,
    val search: TextFieldData = TextFieldData(),
    val list: List<PlaceDetails> = emptyList()
)
