package net.schup.ta

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import kotlinx.serialization.Serializable
import net.schup.ta.cmi.CmiConfiguration
import net.schup.ta.cmi.CmiConnector
import net.schup.ta.cmi.devices.CanDevice

@Serializable
data class CmiData(val cmiUrl: String, val canDevices: List<CanDevice>)

@Controller("/")
class MainController(private val config: CmiConfiguration, private val cmi: CmiConnector) {

    @Get
    fun main(): CmiData {
        return CmiData(config.url ?: "", cmi.getCanDevices());
    }
}