package net.schup.ta.cmi

import io.micronaut.context.annotation.ConfigurationProperties
import io.micronaut.context.annotation.Requires

@ConfigurationProperties(CmiConfiguration.PREFIX)
@Requires(property = CmiConfiguration.PREFIX)
class CmiConfiguration {

    var url: String? = null
    var user: String? = null
    var password: String? = null

    companion object {
        const val PREFIX = "cmi"
    }
}
