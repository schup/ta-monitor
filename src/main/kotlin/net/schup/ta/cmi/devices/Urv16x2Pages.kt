package net.schup.ta.cmi.devices

enum class Urv16x2Pages(val id: String, val title: String) {

    INPUTS("02025800", "Inputs"),
    OUTPUTS("02045800", "Outputs"),
    FUNCTIONS("02055800", "Functions"),
    CAN_LOGGING_ANALOG("02255800", "CAN log analog"),
    CAN_LOGGING_DIGITAL("02265800", "CAN log digital")

}