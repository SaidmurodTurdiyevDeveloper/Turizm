package us.smt.turizm.data.database.local.shared

import android.app.Application
import us.smt.myfinance.data.database.local.shared.StringPreference
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalStorage @Inject constructor(
    application: Application
) {
    private val sharedPreferences = application.getSharedPreferences(
        "myturizm", 0
    )
    var allData by StringPreference(pref = sharedPreferences)
}
