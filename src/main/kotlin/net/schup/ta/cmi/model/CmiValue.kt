package net.schup.ta.cmi.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CmiValue(
    @SerialName("Value") val number: Float,
    @SerialName("Unit") val unit: Unit,
    @SerialName("State") val state: Int? = null,
)
