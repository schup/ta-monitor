package net.schup.ta.cmi.model

import kotlinx.serialization.Serializable

@Serializable
data class Pin(var nr: Int, var name: String, var fadress: Int?) {

    fun normalizedName(): String {
        return normalizeName(name)
    }

    fun isInUse(): Boolean {
        return !name.contains("unbenutzt")
    }

    /**
     * Remove special characters
     */
    private fun normalizeName(name: String): String {
        var norm = name
        UMLAUTE.forEach { from, to ->
            norm = norm.replace(from, to)
        }
        norm = norm.replace("[^a-zA-Z_0-9]".toRegex(), "_")
        norm = norm.replace("_+".toRegex(), "_")
        norm = norm.removeSuffix("_")
        norm = norm.removePrefix("_")
        return norm.lowercase()
    }

    companion object {
        val UMLAUTE = mapOf(
            "\u00fc" to "ue",
            "\u00f6" to "oe",
            "\u00e4" to "ae",
            "\u00df" to "ss",
            "\u00dc" to "UE",
            "\u00d6" to "OE",
            "\u00c4" to "AE"
        )
    }
}