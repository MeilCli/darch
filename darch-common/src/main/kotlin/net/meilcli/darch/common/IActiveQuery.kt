package net.meilcli.darch.common

import kotlin.reflect.KFunction

interface IActiveQuery<TParameter : IParameter, TResult> {

    operator fun invoke(parameter: TParameter): TResult
}