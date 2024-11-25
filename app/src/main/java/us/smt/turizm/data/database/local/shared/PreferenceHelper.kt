package us.smt.myfinance.data.database.local.shared

import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty


class BooleanPreference(
    private val pref: SharedPreferences,
    private val defValue: Boolean = false
) : ReadWriteProperty<Any, Boolean> {
    override fun getValue(thisRef: Any, property: KProperty<*>) =
        pref.getBoolean(property.name, defValue)

    override fun setValue(thisRef: Any, property: KProperty<*>, value: Boolean) =
        pref.edit { putBoolean(property.name, value).apply() }
}

class IntPreference(
    private val pref: SharedPreferences,
    private val defValue: Int = -1
) : ReadWriteProperty<Any, Int> {
    override fun getValue(thisRef: Any, property: KProperty<*>) =
        pref.getInt(property.name, defValue)

    override fun setValue(thisRef: Any, property: KProperty<*>, value: Int) =
        pref.edit { putInt(property.name, value).apply() }
}

class LongPreference(
    private val pref: SharedPreferences,
    private val defValue: Long = 0L
) : ReadWriteProperty<Any, Long> {
    override fun getValue(thisRef: Any, property: KProperty<*>) =
        pref.getLong(property.name, defValue)

    override fun setValue(thisRef: Any, property: KProperty<*>, value: Long) =
        pref.edit { putLong(property.name, value).apply() }
}

class StringPreference(
    private val pref: SharedPreferences,
    private val defValue: String = ""
) : ReadWriteProperty<Any, String> {
    override fun getValue(thisRef: Any, property: KProperty<*>): String =
        pref.getString(property.name, defValue) ?: ""

    override fun setValue(thisRef: Any, property: KProperty<*>, value: String) =
        pref.edit { putString(property.name, value).apply() }
}

class ArrayStringPreference(
    private val pref: SharedPreferences,
    private val defValue: Array<String> = emptyArray()
) : ReadWriteProperty<Any, Array<String>> {
    override fun getValue(thisRef: Any, property: KProperty<*>): Array<String> {
        val arrayGson = pref.getString(property.name, "") ?: ""
        return if (arrayGson.isNotEmpty()) {
            val array = Gson().fromJson<Array<String>>(arrayGson, String::class.java)
            array ?: emptyArray()
        } else emptyArray<String>()
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: Array<String>) {
        val gsonString = Gson().toJson(value)
        if (gsonString.isNullOrBlank())
            pref.edit { putString(property.name, "").apply() }
        else
            pref.edit { putString(property.name, gsonString).apply() }
    }

}