package net.meilcli.darch.loggers

import android.util.Log
import net.meilcli.darch.common.loggers.ILogger
import net.meilcli.darch.common.loggers.LogLevel
import kotlin.reflect.KFunction

class Logger : ILogger {

    override fun log(logLevel: LogLevel, tag: String, message: String) {
        // ToDo: check tag length
        // ToDo: check message length, separate long message
        Log.println(logLevel.level, tag, message)
    }

    override fun log(
        logLevel: LogLevel,
        tag: String,
        callerName: String,
        callingFunction: Function<*>
    ) {
        when (callingFunction) {
            is KFunction<*> -> {
                log(logLevel, tag, "$callerName: ${callingFunction.name}")
            }
            else -> {
                log(logLevel, tag, "$callerName:")
            }
        }
    }
}