package net.schup.ta.cmi.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CmiResult(
    @SerialName("Header") val header: CmiHeader,
    @SerialName("Data") val data: CmiData,
    @SerialName("Status") val status: String,
    @SerialName("Status code") val statusCode: CmiStatus,
) {

}