package com.prasanna.skoproject.data.remote

import com.google.gson.annotations.SerializedName

data class UserResponseDto(
    @SerializedName("data")
    val users: List<UserDto> = emptyList(),
    val page: Int = 0,
    @SerializedName("per_page")
    val perPage: Int = 0,
    val support: SupportDto = SupportDto("", ""),
    val total: Int = 0,
    @SerializedName("total_pages")
    val totalPages: Int = 0
)