package net.schup.ta.cmi.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Json Response Data.
 */
@Serializable
data class CmiData(
    @SerialName("Inputs") val inputs: List<CmiCurrentValue>? = emptyList(),
    @SerialName("Outputs") val outputs: List<CmiCurrentValue>? = emptyList(),
    @SerialName("Logging Analog") val loggingAnalog: List<CmiCurrentValue>? = emptyList(),
    @SerialName("Logging Digital") val loggingDigital: List<CmiCurrentValue>? = emptyList(),
)
