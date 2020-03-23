package net.meilcli.darch.common

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import net.meilcli.darch.common.properties.SelectReactiveProperty
import net.meilcli.darch.common.properties.WhereReactiveProperty

@ExperimentalCoroutinesApi
interface IDarchViewModel : IDarchComponent {

    val coroutineScope: CoroutineScope

    fun <TParameter : IParameter, TResult> activeQuery(function: Function1<TParameter, TResult>): IActiveQuery<TParameter, TResult> {
        return ActiveQuery(logger, function)
    }

    fun <TParameter : IParameter> reactiveCommand(): IReactiveCommand<TParameter> {
        return ReactiveCommand(logger)
    }

    fun <TSource, TResult> IReadOnlyReactiveProperty<TSource>.select(selector: (TSource) -> TResult): IReadOnlyReactiveProperty<TResult> {
        return SelectReactiveProperty(coroutineScope, this, selector)
    }

    fun <T> IReadOnlyReactiveProperty<T>.where(predicate: (T) -> Boolean): IReadOnlyReactiveProperty<T> {
        return WhereReactiveProperty(coroutineScope, this, predicate)
    }
}