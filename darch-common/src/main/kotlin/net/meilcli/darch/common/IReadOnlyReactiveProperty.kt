package net.meilcli.darch.common

import kotlinx.coroutines.channels.ReceiveChannel

interface IReadOnlyReactiveProperty<T> : IDisposable {

    val isValueInitialized: Boolean

    fun openSubscription(): ReceiveChannel<T>

    fun value(): T
}