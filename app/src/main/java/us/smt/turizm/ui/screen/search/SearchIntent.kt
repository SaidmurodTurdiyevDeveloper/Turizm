package us.smt.turizm.ui.screen.search

sealed interface SearchIntent {
    data object Back : SearchIntent
    data object Init : SearchIntent
    data class Search(val text: String) : SearchIntent
    data class OpenDetails(val id: String) : SearchIntent
    data class ChangeFavourite(val id: String) : SearchIntent
}