package us.smt.turizm.utils

interface TextViewError {
    data object Empty : TextViewError
    data object InvalidCharacter : TextViewError
}

fun getErrorText(error: TextViewError): String = when (error) {
    TextViewError.Empty -> "Field is empty"
    TextViewError.InvalidCharacter -> "Invalid character"
     else -> ""
}
