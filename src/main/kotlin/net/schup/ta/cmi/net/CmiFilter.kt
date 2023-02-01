package net.schup.ta.cmi.net

import io.micronaut.context.annotation.Requires
import io.micronaut.http.HttpResponse
import io.micronaut.http.MutableHttpRequest
import io.micronaut.http.annotation.Filter
import io.micronaut.http.filter.ClientFilterChain
import io.micronaut.http.filter.HttpClientFilter
import net.schup.ta.cmi.CmiConfiguration
import org.reactivestreams.Publisher
import java.net.URI

@Filter("/**")
@Requires(condition = CmiFilterCondition::class)
class CmiFilter(private val configuration: CmiConfiguration) : HttpClientFilter {

    override fun doFilter(request: MutableHttpRequest<*>, chain: ClientFilterChain): Publisher<out HttpResponse<*>?> {
        var uri = request.uri
        val uriString = uri.toString()
        if (uriString.contains("%2C")) {
            // CMI doesn't handle URL encoded , for jsonparam value
            uri = URI(uriString.replace("%2C", ","))
            request.uri(uri)
        }
        return chain.proceed(request.basicAuth(configuration.user, configuration.password))
    }
}