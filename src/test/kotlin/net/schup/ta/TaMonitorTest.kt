package net.schup.ta

import io.micronaut.runtime.EmbeddedApplication

import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

@MicronautTest
class TaMonitorTest {
    @Inject
    lateinit var application: EmbeddedApplication<*>

    @Test
    fun testItWorks() {
        assertThat(application.isRunning).isTrue
    }

}