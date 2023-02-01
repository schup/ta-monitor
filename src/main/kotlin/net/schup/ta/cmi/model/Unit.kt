package net.schup.ta.cmi.model

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable(with = UnitSerializer::class)
enum class Unit(val number: Int, val text: String) {
    NONE(0, ""),
    CELSIUS(1, "°C"),
    W_SQUARE_METER(2, "W/m²"),
    LITER_PER_HOUR(3, "l/h"),
    SECOND(4, "sec"),
    MINUTE(5, "min"),
    LITER_PER_IMPULSE(6, "l/Imp"),
    KELVIN(7, "K"),
    PERCENT(8, "%"),
    KILO_WATT(10, "KW"),
    KILO_WATT_HOURS(11, "kWh"),
    MEGA_WATT_HOURS(12, "MWh"),
    VOLT(13, "V"),
    MILLI_AMPERE(14, "mA"),
    HOURS(15, "hr"),
    DAYS(16, "days"),
    IMPULSE(17, "Imp"),
    KILO_OHM(18, "kΩ"),
    LITER(19, "l"),
    KILOMETER_PER_HOUR(20, "km/h"),
    HERTZ(21, "Hz"),
    LITER_PER_MINUTE(22, "l/min"),
    BAR(23, "bar"),
    NONE24(24, ""),
    KILOMETER(25, "km"),
    METER(26, "m"),
    MILLIMETER(27, "mm"),
    CUBIC_METER(28, "m³"),
    LITER_PER_DAY(35, "l/d"),
    METER_PER_SECOND(36, "m/s"),
    CUBIC_METER_PER_SECOND(37, "m³/min"),
    CUBIC_METER_PER_HOUR(38, "m³/h"),
    CUBIC_METER_PER_DAY(39, "m³/d"),
    MILLIMETER_PER_MINUTE(40, "mm/min"),
    MILLIMETER_PER_HOUR(41, "mm/h"),
    MILLIMETER_PER_DAY(42, "mm/d"),
    ON_OFF(43, "ON/OFF"),
    NO_YES(44, "NO/YES"),
    CELSIUS2(46, "°C"),
    EURO(50, "€"),
    DOLLAR(51, "$")
}

object UnitSerializer : KSerializer<Unit> {

    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("UnitByNumber", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): Unit {
        val unitAsString = decoder.decodeString()
        val unitValue = unitAsString.toInt()
        return Unit.values().first { unit -> unit.number == unitValue }
    }

    override fun serialize(encoder: Encoder, value: Unit) {
        encoder.encodeString(value.number.toString())
    }

}