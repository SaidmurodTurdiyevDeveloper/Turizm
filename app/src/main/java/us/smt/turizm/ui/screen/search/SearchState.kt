package us.smt.turizm.ui.screen.search

import us.smt.turizm.domen.model.PlaceData
import us.smt.turizm.ui.utils.TextFieldData

data class SearchState(
    val search: TextFieldData = TextFieldData(),
    val list: List<PlaceData> = emptyList()
)
