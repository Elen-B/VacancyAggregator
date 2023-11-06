package ru.practicum.android.diploma.core.util

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

class ErrorInfo(
    @StringRes val message: Int? = null,
    @DrawableRes val image: Int? = null
)
