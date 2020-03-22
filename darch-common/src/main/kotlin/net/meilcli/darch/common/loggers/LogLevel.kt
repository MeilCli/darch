package net.meilcli.darch.common.loggers

import android.util.Log

enum class LogLevel(val level: Int) {
    Info(Log.INFO),
    Assert(Log.ASSERT),
    Debug(Log.DEBUG),
    Error(Log.ERROR),
    Verbose(Log.VERBOSE),
    Warn(Log.WARN)
}