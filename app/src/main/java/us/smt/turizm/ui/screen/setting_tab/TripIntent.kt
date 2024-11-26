package us.smt.turizm.ui.screen.setting_tab

sealed interface TripIntent {
    data object Init : TripIntent
    data class OpenItem(val id: String) : TripIntent
}