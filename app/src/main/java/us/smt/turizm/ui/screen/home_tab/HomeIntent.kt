package us.smt.turizm.ui.screen.home_tab

import us.smt.turizm.domen.model.PlaceDetails

sealed interface HomeIntent {
    data object Init : HomeIntent
    data object Back : HomeIntent
    data object OpenSearch : HomeIntent
    data class OpenDetails(val id: String) : HomeIntent
    data class ChangeFavourite(val data: PlaceDetails) : HomeIntent
}