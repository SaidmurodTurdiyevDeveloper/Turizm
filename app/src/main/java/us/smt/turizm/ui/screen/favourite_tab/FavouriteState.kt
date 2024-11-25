package us.smt.turizm.ui.screen.favourite_tab

import us.smt.turizm.domen.model.PlaceData

data class FavouriteState(
    val favourites: List<PlaceData> = emptyList()
)
