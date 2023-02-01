package net.schup.ta.cmi.devices

import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import net.schup.ta.cmi.net.CmiApiClient
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

@MicronautTest
internal class Urv16x2Test {

    @Inject
    lateinit var client: CmiApiClient
    
    @Test
    fun parseInputs() {
        val device = Urv16x2(client, 2)
        device.parseInputs(ClassLoader.getSystemResource("urv16x2/de/02025800-inputs.html").readText())
    }

    @Test
    fun parseOutputs() {
        val device = Urv16x2(client, 2)
        device.parseOutputs(ClassLoader.getSystemResource("urv16x2/de/02045800-outputs.html").readText())
    }

    @Test
    fun parseCanLoggingAnalog() {
        val device = Urv16x2(client, 2)
        val pins =
            device.parseOutputs(ClassLoader.getSystemResource("urv16x2/de/02255800-can-logging-analog.html").readText())
        assertThat(pins).hasSize(16)
    }

    @Test
    fun parseCanLoggingDigital() {
        val device = Urv16x2(client, 2)
        val pins =
            device.parseOutputs(
                ClassLoader.getSystemResource("urv16x2/de/02005800-can-logging-digital.html").readText()
            )
        assertThat(pins).hasSize(5)
    }
}