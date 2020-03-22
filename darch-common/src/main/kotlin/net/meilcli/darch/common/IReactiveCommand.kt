package net.meilcli.darch.common

interface IReactiveCommand<TParameter : IParameter> {

    fun execute(parameter: TParameter)

    fun register(action: (TParameter) -> Unit)

    fun unregister(action: (TParameter) -> Unit)
}