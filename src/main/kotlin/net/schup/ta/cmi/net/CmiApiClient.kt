package net.schup.ta.cmi.net

import io.micronaut.core.async.annotation.SingleResult
import io.micronaut.http.HttpHeaders.ACCEPT
import io.micronaut.http.HttpHeaders.USER_AGENT
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Header
import io.micronaut.http.annotation.Headers
import io.micronaut.http.annotation.QueryValue
import io.micronaut.http.client.annotation.Client

@Headers(
    Header(name = USER_AGENT, value = "CMI HTTP Client"),
    Header(name = ACCEPT, value = "*")
)
@Client("\${cmi.url}")
interface CmiApiClient {

    // params will be URLencoded, , = %2C, CMI can't handle it :-(
    /**
     * Gets the data from the CAN Bus device.
     * @param nodeId the CAN Bus node ID.
     * @param params the jsonparam value, defaults to "I" as CMI produces invalid JSON if empty
     */
    @Get("/INCLUDE/api.cgi?jsonnode={nodeId}&jsonparam={params}")
    @SingleResult
    fun getData(@QueryValue nodeId: Int, @QueryValue(defaultValue = "I") params: String): String

    @Get("/INCLUDE/devpagex.cgi?pagex2={pagex2}")
    fun getDevicePageX2(@QueryValue pagex2: String): String

    @Get("/INCLUDE/can_nodes.cgi")
    fun getCanNodeIds(): String

    @Get("/can_knoten.cgi?node={nodeId}")
    fun getCanNodeDetails(@QueryValue nodeId: Int): String
}