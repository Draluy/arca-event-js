package com.example.model

import com.fasterxml.jackson.annotation.JsonProperty

data class User(
    @JsonProperty("id") val id: Long,
    @JsonProperty("first_name") val first_name: String,
    @JsonProperty("last_name") val last_name: String,
    @JsonProperty("email") val email: String,
    @JsonProperty("address") val address: String
)