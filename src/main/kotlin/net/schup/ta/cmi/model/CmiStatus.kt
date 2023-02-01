package net.schup.ta.cmi.model

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable(with=CmiStatusSerializer::class)
enum class CmiStatus(val code: Int, val description: String) {
    OK(0, ""),
    NODE_ERROR(1, "Node not available"),
    FAIL(2, "Failure during the CAN-request/parameter not available for this device"),
    SYNTAX_ERROR(3, "Error in the request String"),
    TOO_MANY_REQUESTS(4, "Only one request per minute is permitted"),
    DEVICE_NOT_SUPPORTED(5, "Device not supported"),
    TOO_FEW_ARGUMENTS(6, "jsonnode or jsonparam not set"),
    CAN_BUSY(7, "CAN Bus is busy"),
    ERROR(-1, "Any other error")
}

object CmiStatusSerializer : KSerializer<CmiStatus> {

    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("CmiStatusByCode", PrimitiveKind.INT)

    override fun deserialize(decoder: Decoder): CmiStatus {
        val statusCode = decoder.decodeInt()
        return CmiStatus.values().firstOrNull() { status -> status.code == statusCode } ?: CmiStatus.ERROR
    }

    override fun serialize(encoder: Encoder, value: CmiStatus) {
        encoder.encodeString(value.code.toString())
    }

}