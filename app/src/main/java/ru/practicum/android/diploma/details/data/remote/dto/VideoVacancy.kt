package ru.practicum.android.diploma.details.data.remote.dto


import com.google.gson.annotations.SerializedName

data class VideoVacancy(
    @SerializedName("video_url")
    val videoUrl: String
)