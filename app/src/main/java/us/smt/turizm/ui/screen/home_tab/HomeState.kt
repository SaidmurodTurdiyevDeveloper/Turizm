package us.smt.turizm.ui.screen.home_tab

import us.smt.turizm.domen.model.PlaceDetails

data class HomeState(
    val isLoading: Boolean = true,
    val popular: List<PlaceDetails> = emptyList(),
    val near: List<PlaceDetails> = emptyList()
)
