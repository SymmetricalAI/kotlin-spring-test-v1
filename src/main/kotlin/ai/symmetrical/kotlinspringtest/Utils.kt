package ai.symmetrical.kotlinspringtest

import org.slf4j.LoggerFactory.getLogger
import kotlin.reflect.full.companionObject

fun <R : Any> R.logger() = lazy { getLogger(this.javaClass.let { unwrapCompanionClass(it).name }) }

fun <T : Any> unwrapCompanionClass(ofClass: Class<T>): Class<*> =
    ofClass.enclosingClass?.takeIf { it.kotlin.companionObject?.java == ofClass } ?: ofClass
