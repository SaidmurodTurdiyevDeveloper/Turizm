package us.smt.turizm.ui.screen.favourite_tab

import us.smt.turizm.domen.model.PlaceDetails

data class FavouriteState(
    val isLoading: Boolean = true,
    val favourites: List<PlaceDetails> = emptyList()
)
