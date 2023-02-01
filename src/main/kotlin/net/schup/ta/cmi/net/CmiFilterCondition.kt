package net.schup.ta.cmi.net

import io.micronaut.context.condition.Condition
import io.micronaut.context.condition.ConditionContext
import io.micronaut.context.exceptions.NoSuchBeanException
import net.schup.ta.cmi.CmiConfiguration

class CmiFilterCondition : Condition {
    override fun matches(context: ConditionContext<*>?): Boolean {
        if (context != null && context.beanContext != null) {
            try {
                val cmiConfiguration: CmiConfiguration =
                    context.beanContext.getBean(CmiConfiguration::class.java)
                if (cmiConfiguration.user != null && cmiConfiguration.password != null) {
                    return true
                }
            } catch (e: NoSuchBeanException) {
            }
        }
        return false
    }
}