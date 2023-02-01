package net.schup.ta.cmi.model

import kotlinx.serialization.Serializable

@Serializable
data class PinValue(
    val nr: Int,
    val name: String,
    val normalizedName: String,
    val value: Float,
    val unit: String,
    val state: Int? = null,
    val pinType: PinType,
    val faddress: Int?


)