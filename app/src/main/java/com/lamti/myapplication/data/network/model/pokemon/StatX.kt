package com.lamti.myapplication.data.network.model.pokemon


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StatX(
    @SerialName("name")
    val name: String,
    @SerialName("url")
    val url: String
)