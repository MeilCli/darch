package net.meilcli.darch.common

interface IStream<T> : IReadOnlyStream<T> {

    fun add(element: T)

    fun addAll(elements: Collection<T>)

    fun remove(element: T)

    fun removeAt(index: Int)

    fun change(index: Int, element: T)

    fun changeAll(selector: (T) -> T)

    fun clear()
}