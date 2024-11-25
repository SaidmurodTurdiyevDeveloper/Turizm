package us.smt.turizm.ui.screen.favourite_tab

sealed interface FavouriteIntent {
    data object Init:FavouriteIntent
    data class Favourite(val id: String) : FavouriteIntent
    data class OpenDetails(val id: String) : FavouriteIntent
}