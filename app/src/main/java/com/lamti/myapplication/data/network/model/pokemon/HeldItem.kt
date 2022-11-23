package com.lamti.myapplication.data.network.model.pokemon


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HeldItem(
    @SerialName("item")
    val item: Item,
    @SerialName("version_details")
    val versionDetails: List<VersionDetail>
)