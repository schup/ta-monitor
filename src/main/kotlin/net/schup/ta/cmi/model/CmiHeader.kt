package net.schup.ta.cmi.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import net.schup.ta.cmi.devices.Device
import java.time.LocalDateTime
import java.time.ZoneOffset

@Serializable
data class CmiHeader(
    @SerialName("Version") val version: Int,
    @SerialName("Device") val device: Device,
    @SerialName("Timestamp") val timestamp: Long
) {

    fun getTimestamp(): LocalDateTime {
        return LocalDateTime.ofEpochSecond(timestamp, 0, ZoneOffset.MIN)
    }

}