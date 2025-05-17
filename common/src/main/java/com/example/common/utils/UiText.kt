package com.example.common.utils

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.ui.res.stringResource

sealed class UiText {
    data class RemoteString(val message: String) : UiText()
    class LocalString(@StringRes val res: Int, vararg val args: Any) : UiText()

    data object Idle : UiText()

    fun getString(): String {
        return when (this) {
            is RemoteString -> message
            is LocalString -> stringResource(res, *args)
            Idle -> ""
        }
    }
}