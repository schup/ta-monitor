package net.schup.ta.cmi.devices

interface CanDevice {

    fun getNodeId(): Int

    fun getDeviceType(): Device
}