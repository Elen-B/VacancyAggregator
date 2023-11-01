package ru.practicum.android.diploma.details.data.remote.dto


import com.google.gson.annotations.SerializedName

data class Test(
    @SerializedName("required")
    val required: Boolean
)