package net.meilcli.darch.common

interface IReadOnlyStream<T> {

    val size: Int

    fun get(index: Int): T

    fun indexOf(element: T): Int

    fun registerNotifyStreamChanged(notifyStreamChanged: INotifyStreamChanged)

    fun unregisterNotifyStreamChanged(notifyStreamChanged: INotifyStreamChanged)
}