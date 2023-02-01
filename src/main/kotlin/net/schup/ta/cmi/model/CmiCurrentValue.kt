package net.schup.ta.cmi.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CmiCurrentValue(
    @SerialName("Number") val number: Int,
    @SerialName("AD") val ad: AnalogDigital,
    @SerialName("Value") val value: CmiValue
) {

    fun getStringValue(): String {
        return "" + value.number + value.unit.text
    }
}
