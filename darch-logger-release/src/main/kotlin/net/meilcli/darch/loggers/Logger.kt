package net.meilcli.darch.loggers

import net.meilcli.darch.common.loggers.ILogger
import net.meilcli.darch.common.loggers.LogLevel

class Logger : ILogger {

    override fun log(logLevel: LogLevel, tag: String, message: String) {

    }

    override fun log(
        logLevel: LogLevel,
        tag: String,
        callerName: String,
        callingFunction: Function<*>
    ) {

    }
}