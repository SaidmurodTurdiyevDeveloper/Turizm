package us.smt.turizm.ui.screen.home_tab

import us.smt.turizm.domen.model.PlaceData

data class HomeState(
    val popular: List<PlaceData> = emptyList(),
    val near: List<PlaceData> = emptyList()
)
