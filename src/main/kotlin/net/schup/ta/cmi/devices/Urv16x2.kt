package net.schup.ta.cmi.devices

import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import net.schup.ta.cmi.model.*
import net.schup.ta.cmi.net.CmiApiClient
import org.jsoup.Jsoup
import org.slf4j.LoggerFactory

@Serializable
class Urv16x2(private val client: CmiApiClient, private val nodeId: Int) : CanDevice {

    private val log = LoggerFactory.getLogger(this.javaClass)

    private var pins: MutableMap<PinType, List<Pin>> = mutableMapOf()

    private var values: List<PinValue> = emptyList()

    fun updatePins(): Urv16x2 {
        pins[PinType.INPUT] = loadPins(Urv16x2Pages.INPUTS)
        pins[PinType.OUTPUT] = loadPins(Urv16x2Pages.OUTPUTS)
        pins[PinType.CAN_LOG_ANALOG] = loadPins(Urv16x2Pages.CAN_LOGGING_ANALOG)
        pins[PinType.CAN_LOG_DIGITAL] = loadPins(Urv16x2Pages.CAN_LOGGING_DIGITAL)
        return this
    }

    private fun loadPins(type: Urv16x2Pages): List<Pin> {
        val html = client.getDevicePageX2(type.id)
        return parseOutputs(html)
    }

    /**
     * Get the last read values.
     */
    fun getValues(): List<PinValue> {
        return values
    }

    fun getPins(): Map<PinType, List<Pin>> {
        return pins
    }

    private fun loadData(): CmiResult {
        log.debug("getData from $nodeId")
        val json = client.getData(nodeId, "I,O,La,Ld")
        return Json.decodeFromString(json)
    }

    fun updateValues(): List<PinValue> {
        val data = loadData()
        val pinValues = mutableListOf<PinValue?>()
        data.data.inputs?.forEach { i ->
            pinValues.add(pinValue(PinType.INPUT, i))
        }
        data.data.outputs?.forEach { i ->
            pinValues.add(pinValue(PinType.OUTPUT, i))
        }
        data.data.loggingAnalog?.forEach { i ->
            pinValues.add(pinValue(PinType.CAN_LOG_ANALOG, i))
        }
        data.data.loggingDigital?.forEach { i ->
            pinValues.add(pinValue(PinType.CAN_LOG_DIGITAL, i))
        }
        values = pinValues.filterNotNull()
        return values
    }

    private fun pinValue(pinType: PinType, cmiCurrentValue: CmiCurrentValue): PinValue? {
        val pin = getPin(pinType, cmiCurrentValue.number)
        var pinValue: PinValue? = null
        if (pin != null) {
            val value = cmiCurrentValue.value
            pinValue = PinValue(
                cmiCurrentValue.number,
                pin.name,
                pin.normalizedName(),
                value.number,
                value.unit.text,
                value.state,
                pinType,
                pin.fadress
            )
        }
        return pinValue
    }

    private fun getPin(pinType: PinType, pinNumber: Int): Pin? {
        return pins[pinType]?.filter { it.nr == pinNumber }?.first()
    }

    private fun parsePins(html: String): List<Pin> {
        val doc = Jsoup.parse(html)
        //val headline = doc.select("div.HEADLINE").text()
        val pin = doc.select("div.BOX")
            .map { box ->
                val fadressString = box.attr("fadresse")
                var fadress: Int? = null
                if (fadressString.isNotEmpty()) {
                    fadress = fadressString.toInt(16) // hex value
                }
                val label = box.select("div.bez").text()
                // val value = box.select("div.val").text()
                val name = label.substringAfter(":").trim()
                val nr = label.substringBefore(":").trim()
                Pin(nr.toInt(), name, fadress)
            }
            .filter { it.isInUse() }
            .toList()
        return pin
    }

    fun parseInputs(html: String): List<Pin> {
        return parsePins(html)
    }

    fun parseOutputs(html: String): List<Pin> {
        return parseInputs(html) // structure is the same as input, we don't care about values
    }

    override fun getNodeId(): Int {
        return nodeId
    }

    override fun getDeviceType(): Device {
        return Device.UVR16X2
    }

}