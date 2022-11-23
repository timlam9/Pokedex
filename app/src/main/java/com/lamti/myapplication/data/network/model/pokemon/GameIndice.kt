package com.lamti.myapplication.data.network.model.pokemon


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GameIndice(
    @SerialName("game_index")
    val gameIndex: Int,
    @SerialName("version")
    val version: Version
)