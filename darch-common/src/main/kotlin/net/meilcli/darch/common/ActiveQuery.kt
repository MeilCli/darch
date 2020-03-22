package net.meilcli.darch.common

import net.meilcli.darch.common.loggers.ILogger
import net.meilcli.darch.common.loggers.LogLevel

class ActiveQuery<TParameter : IParameter, TResult>(
    private val logger: ILogger,
    private val function: Function1<TParameter, TResult>
) : IActiveQuery<TParameter, TResult> {

    override fun invoke(parameter: TParameter): TResult {
        logger.log(LogLevel.Debug, ILogger.defaultTag, "ActiveQuery", function)
        return function(parameter)
    }
}