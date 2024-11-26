package us.smt.turizm.ui.screen.setting_tab

import us.smt.turizm.domen.model.PlaceDetails

data class TripState(
    val isLoading:Boolean=true,
    val tripList: List<PlaceDetails> = emptyList(),
)
