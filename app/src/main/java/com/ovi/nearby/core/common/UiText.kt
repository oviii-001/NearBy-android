package com.ovi.nearby.core.common

sealed class UiText {
    data class DynamicString(val value: String) : UiText()
    data class StringResource(val resId: Int, val args: Array<Any> = emptyArray()) : UiText()

    fun asString(): String = when (this) {
        is DynamicString -> value
        is StringResource -> value.toString()
    }
}
