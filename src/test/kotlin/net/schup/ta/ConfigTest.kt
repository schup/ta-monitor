package net.schup.ta

import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import net.schup.ta.cmi.CmiConfiguration
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

@MicronautTest
class ConfigTest(private val config: CmiConfiguration) {

    @Test
    fun testInputConfig() {
        assertThat(config.url).isNotBlank
    }
}