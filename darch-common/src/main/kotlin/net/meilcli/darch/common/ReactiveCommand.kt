package net.meilcli.darch.common

import net.meilcli.darch.common.loggers.ILogger
import net.meilcli.darch.common.loggers.LogLevel

class ReactiveCommand<TParameter : IParameter>(
    private val logger: ILogger
) : IReactiveCommand<TParameter> {

    private val actions = mutableListOf<(TParameter) -> Unit>()

    override fun execute(parameter: TParameter) {
        logger.log(LogLevel.Debug, ILogger.defaultTag, "ReactiveCommand execute")
        for (action in actions) {
            action(parameter)
        }
    }

    override fun register(action: (TParameter) -> Unit) {
        actions += action
    }

    override fun unregister(action: (TParameter) -> Unit) {
        actions -= action
    }
}