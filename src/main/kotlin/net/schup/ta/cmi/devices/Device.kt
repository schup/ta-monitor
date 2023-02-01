package net.schup.ta.cmi.devices

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable(with = DeviceSerializer::class)
enum class Device(val id: Int, val deviceName: String) {
    COE(0x7F, "CoE"),
    UVR1611(0x80, "UVR1611"),
    CAN_MT(0x81, "CAN-MT"),
    CAN_I_O44(0x82, "CAN-I/O44"),
    CAN_I_O35(0x83, "CAN-I/O35"),
    CAN_BC(0x84, "CAN-BC"),
    CAN_EZ(0x85, "CAN-EZ"),
    CAN_TOUCH(0x86, "CAN-TOUCH"),
    UVR16X2(0x87, "UVR16x2"),
    RSM610(0x88, "RSM610"),
    CAN_I_O45(0x89, "CAN-I/O45"),
    CMI(0x8A, "CMI"),
    CAN_EZ2(0x8B, "CAN-EZ2"),
    CAN_MTX2(0x8C, "CAN-MTx2"),
    CAN_BC2(0x8D, "CAN-BC2"),
    UVR65(0x8E, "UVR65"),
    CAN_EZ3(0x8F, "CAN-EZ3"),
    UVR610(0x91, "UVR610"),
    UVR67(0x92, "UVR67"),
    BL_NET(0xA3, "UVR67")

}

object DeviceSerializer : KSerializer<Device> {

    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("DeviceById", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): Device {
        val deviceIdAsString = decoder.decodeString()
        val deviceValue = deviceIdAsString.toInt(16)
        return Device.values().first { device -> device.id == deviceValue }
    }

    override fun serialize(encoder: Encoder, value: Device) {
        encoder.encodeString(value.id.toString(16))
    }

}