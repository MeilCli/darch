package net.meilcli.darch.common.loggers

interface ILogger {

    companion object {

        const val defaultTag = "darch"
    }

    fun log(logLevel: LogLevel, tag: String, message: String)

    fun log(
        logLevel: LogLevel,
        tag: String,
        callerName: String,
        callingFunction: Function<*>
    )
}