package net.schup.ta.cmi.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class PinTest {

    @Test
    fun normalizedName() {
        assertThat(
            Pin(
                1,
                "**A name   with %$*'  characters%%",
                12
            ).normalizedName()
        ).isEqualTo("a_name_with_characters")
    }
}