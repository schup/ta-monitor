package net.schup.ta.cmi.model

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import net.schup.ta.cmi.devices.Device
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.io.File

@OptIn(kotlinx.serialization.ExperimentalSerializationApi::class)
class CmiDataTest {

    @Test
    fun testParse() {
        File(ClassLoader.getSystemResource("test-data/data1.json").file).inputStream().use { stream ->
            val data = Json.decodeFromStream<CmiResult>(stream)
            println(data)
            assertThat(data.header.device).isEqualTo(Device.UVR16X2)
            assertThat(data.header.getTimestamp().toString()).isEqualTo("2021-12-16T16:06:38")
            println(data.status)
        }
    }


}