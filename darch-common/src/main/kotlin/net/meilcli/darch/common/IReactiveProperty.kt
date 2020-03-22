package net.meilcli.darch.common

interface IReactiveProperty<T> : IReadOnlyReactiveProperty<T> {

    fun offer(value: T): Boolean
}