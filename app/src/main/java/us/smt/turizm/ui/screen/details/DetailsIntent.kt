package us.smt.turizm.ui.screen.details

sealed interface DetailsIntent {
    data object Back : DetailsIntent
    data class LoadData(val id: String) : DetailsIntent
    data object FavoriteChange : DetailsIntent
}