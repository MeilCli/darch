package net.meilcli.darch.common.streams

import net.meilcli.darch.common.INotifyStreamChanged
import net.meilcli.darch.common.IStream
import net.meilcli.darch.common.StreamEvent

class Stream<T> : IStream<T> {

    private val source = mutableListOf<T>()
    private val eventNotifiers = mutableListOf<INotifyStreamChanged>()

    override val size: Int
        get() = source.size

    override fun add(element: T) {
        source.add(element)
        raiseEvent(StreamEvent.Added(this, source.lastIndex))
    }

    override fun addAll(elements: Collection<T>) {
        val startIndex = source.size
        source.addAll(elements)
        raiseEvent(StreamEvent.RangeAdded(this, startIndex, elements.size))
    }

    override fun remove(element: T) {
        val index = source.indexOf(element)
        if (0 <= index) {
            source.removeAt(index)
            raiseEvent(StreamEvent.Removed(this, index))
        }
    }

    override fun removeAt(index: Int) {
        source.removeAt(index)
        raiseEvent(StreamEvent.Removed(this, index))
    }

    override fun change(index: Int, element: T) {
        source[index] = element
        raiseEvent(StreamEvent.Changed(this, index))
    }

    override fun changeAll(selector: (T) -> T) {
        for (i in 0 until source.size) {
            val oldElement = source[i]
            val newElement = selector(oldElement)
            if (oldElement != newElement) {
                source[i] = newElement
                raiseEvent(StreamEvent.Changed(this, i))
            }
        }
    }

    override fun clear() {
        source.clear()
        raiseEvent(StreamEvent.Reset(this))
    }

    override fun get(index: Int): T {
        return source[index]
    }

    override fun indexOf(element: T): Int {
        return source.indexOf(element)
    }

    @Suppress("NOTHING_TO_INLINE")
    private inline fun raiseEvent(event: StreamEvent) {
        for (eventNotifier in eventNotifiers) {
            eventNotifier.eventRaised(event)
        }
    }

    override fun registerNotifyStreamChanged(notifyStreamChanged: INotifyStreamChanged) {
        if (eventNotifiers.contains(notifyStreamChanged)) {
            return
        }
        eventNotifiers.add(notifyStreamChanged)
    }

    override fun unregisterNotifyStreamChanged(notifyStreamChanged: INotifyStreamChanged) {
        eventNotifiers.remove(notifyStreamChanged)
    }
}