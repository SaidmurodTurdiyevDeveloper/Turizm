package us.smt.turizm.ui.screen.home_tab

sealed interface HomeIntent {
    data object Init : HomeIntent
    data object Back : HomeIntent
    data object OpenSearch : HomeIntent
    data class OpenDetails(val id: String) : HomeIntent
    data class ChangeFavourite(val id: String) : HomeIntent
}