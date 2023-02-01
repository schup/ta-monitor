package net.schup.ta.cmi.devices

import net.schup.ta.cmi.net.CmiApiClient

class GenericDevice(private val client: CmiApiClient, private val nodeId: Int, private val device: Device) : CanDevice {
    override fun getNodeId(): Int {
        return nodeId
    }

    override fun getDeviceType(): Device {
        return device
    }

}