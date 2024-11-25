package us.smt.turizm.ui.utils

import us.smt.turizm.utils.TextViewError


data class TextFieldData(
    val success: Boolean = false,
    val text: String = "",
    val error: TextViewError? = null
)
