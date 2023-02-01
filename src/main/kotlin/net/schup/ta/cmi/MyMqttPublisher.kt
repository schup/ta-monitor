package net.schup.ta.cmi

import io.micronaut.mqtt.annotation.Qos
import io.micronaut.mqtt.annotation.Retained
import io.micronaut.mqtt.annotation.Topic
import io.micronaut.mqtt.v3.annotation.MqttPublisher

@MqttPublisher
interface MyMqttPublisher {

    @Qos(1)
    @Retained(true)
    fun send(@Topic topic: String, data: ByteArray)
}