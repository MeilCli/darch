package net.meilcli.darch.common.loggers

fun ILogger.info(tag: String, message: String) {
    log(LogLevel.Info, tag, message)
}

fun ILogger.assert(tag: String, message: String) {
    log(LogLevel.Assert, tag, message)
}

fun ILogger.debug(tag: String, message: String) {
    log(LogLevel.Debug, tag, message)
}

fun ILogger.error(tag: String, message: String) {
    log(LogLevel.Error, tag, message)
}

fun ILogger.verbose(tag: String, message: String) {
    log(LogLevel.Verbose, tag, message)
}

fun ILogger.warn(tag: String, message: String) {
    log(LogLevel.Warn, tag, message)
}