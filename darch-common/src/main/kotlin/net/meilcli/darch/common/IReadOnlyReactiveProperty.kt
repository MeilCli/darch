package net.meilcli.darch.common

import kotlinx.coroutines.channels.ReceiveChannel

interface IReadOnlyReactiveProperty<T> : IDisposable {

    fun openSubscription(): ReceiveChannel<T>
}