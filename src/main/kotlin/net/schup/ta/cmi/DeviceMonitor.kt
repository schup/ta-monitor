package net.schup.ta.cmi

import io.micronaut.context.annotation.Context
import io.micronaut.scheduling.annotation.Scheduled
import jakarta.annotation.PostConstruct
import jakarta.inject.Inject
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import net.schup.ta.cmi.devices.Device
import net.schup.ta.cmi.devices.Urv16x2
import net.schup.ta.cmi.model.PinValue
import org.slf4j.LoggerFactory
import java.nio.charset.StandardCharsets


@Serializable
data class DeviceData(val deviceName: String, val nodeId: Int, val values: List<PinValue>)

@Context
class DeviceMonitor {

    private val log = LoggerFactory.getLogger(this.javaClass)

    @Inject
    lateinit var cmiConnector: CmiConnector

    @Inject
    lateinit var mqtt: MyMqttPublisher

    @OptIn(ExperimentalSerializationApi::class)
    private val jsonFormat = Json { explicitNulls = false; prettyPrint = false }

    @PostConstruct
    fun init() {
        val devices = cmiConnector.getCanDevices()
        log.info("found ${devices.size} device(s): ${devices.joinToString(", ") { "${it.getNodeId()}: ${it.getDeviceType().deviceName}" }}")
    }

    @Scheduled(fixedRate = "1m", initialDelay = "1m")
    internal fun updateValues() {
        cmiConnector.getCanDevices().forEach { device ->
            if (device.getDeviceType() == Device.UVR16X2) {
                val values = (device as Urv16x2).updateValues()
                if (log.isDebugEnabled) {
                    log.debug(values.joinToString("\n") {
                        "${it.normalizedName} = ${it.value} ${it.unit}"
                    })
                }
                values.forEach { pinValue ->
                    val data = pinValue.value.toString().toByteArray()
                    val topic = "cmi/sensors/sensor/${pinValue.normalizedName}/state"
                    log.debug("publish ${pinValue.value} to $topic")
                    mqtt.send(topic, data)
                }
                val json = jsonFormat.encodeToString(
                    DeviceData(
                        device.getDeviceType().deviceName,
                        device.getNodeId(), values
                    )
                )
                mqtt.send("cmi/${device.getNodeId()}/json", json.toByteArray(StandardCharsets.UTF_8))
                log.debug("values: $json")

            } else {
                // unsupported device
            }
        }
    }
}