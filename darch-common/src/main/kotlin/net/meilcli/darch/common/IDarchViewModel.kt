package net.meilcli.darch.common

interface IDarchViewModel : IDarchComponent {

    fun <TParameter : IParameter, TResult> activeQuery(function: Function1<TParameter, TResult>): IActiveQuery<TParameter, TResult> {
        return ActiveQuery(logger, function)
    }

    fun <TParameter : IParameter> reactiveCommand(): IReactiveCommand<TParameter> {
        return ReactiveCommand(logger)
    }
}