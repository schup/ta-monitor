package net.schup.ta.cmi

import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import net.schup.ta.cmi.devices.Device
import net.schup.ta.cmi.devices.Urv16x2
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

@MicronautTest
internal class CmiConnectorTest {

    @Inject
    lateinit var cmiConnector: CmiConnector

    @Test
    fun getCanDevices() {
        val devices = cmiConnector.loadCanDevices()
        assertThat(devices).hasSize(1)
        assertThat(devices.first().getDeviceType()).isEqualTo(Device.UVR16X2)

        val device = devices.first() as Urv16x2
        assertThat(device.getPins()).isNotEmpty
    }
}