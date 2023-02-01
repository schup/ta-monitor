package net.schup.ta.cmi

import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import net.schup.ta.cmi.devices.CanDevice
import net.schup.ta.cmi.devices.Device
import net.schup.ta.cmi.devices.GenericDevice
import net.schup.ta.cmi.devices.Urv16x2
import net.schup.ta.cmi.model.CmiResult
import net.schup.ta.cmi.net.CmiApiClient
import org.jsoup.Jsoup
import javax.annotation.PostConstruct


@Singleton
class CmiConnector {

    @Inject
    lateinit var client: CmiApiClient

    private lateinit var canDevices: List<CanDevice>

    @PostConstruct
    fun init() {
        refreshDevices()
    }

    fun refreshDevices() {
        canDevices = loadCanDevices()
    }

    fun getCanDevices(): List<CanDevice> {
        return canDevices
    }

    fun loadCanDevices(): List<CanDevice> {
        return client.getCanNodeIds().split(";")
            .filter { it.isNotBlank() }
            .map { it.toInt() }
            .map { id ->
                val data = getCmiData(id, "I")
                when (data.header.device) {
                    Device.UVR16X2 -> Urv16x2(client, id).updatePins()
                    else -> GenericDevice(client, id, data.header.device)
                }
            }
    }

    private fun getCmiData(nodeId: Int, params: String): CmiResult {
        val json = client.getData(nodeId, params)
        return Json.decodeFromString(json)
    }

    internal fun getNodeDetails(nodeId: Int): String {
        val html = client.getCanNodeDetails(nodeId)
        val doc = Jsoup.parse(html)
        return doc.select("div.nodebez").textNodes()[1].text().trim()
    }

}