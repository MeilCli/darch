package net.meilcli.darch.common.streams

import net.meilcli.darch.common.INotifyStreamChanged
import net.meilcli.darch.common.IReadOnlyStream
import net.meilcli.darch.common.StreamEvent

// will be remove this class
class CombineStream<T>(
    private val source1: IReadOnlyStream<T>,
    private val source2: IReadOnlyStream<T>
) : IReadOnlyStream<T>, INotifyStreamChanged {

    private val source = mutableListOf<T>()
    private val eventNotifiers = mutableListOf<INotifyStreamChanged>()

    init {
        source1.registerNotifyStreamChanged(this)
        source2.registerNotifyStreamChanged(this)
        for (i in 0 until source1.size) {
            source += source1.get(i)
        }
        for (i in 0 until source2.size) {
            source += source2.get(i)
        }
        raiseEvent(StreamEvent.RangeAdded(this, 0, size))
    }

    override val size: Int
        get() = source.size

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

    override fun eventRaised(event: StreamEvent) {
        when (event.sender) {
            source1 -> eventRaised(event, source1)
            source2 -> eventRaised(event, source2)
        }
    }

    private fun eventRaised(event: StreamEvent, eventSource: IReadOnlyStream<T>) {
        when (event) {
            is StreamEvent.Added -> {
                source += eventSource.get(event.index)
                eventRaised(StreamEvent.Added(this, source.lastIndex))
            }
            is StreamEvent.RangeAdded -> {
                val start = size
                for (i in event.range) {
                    source += eventSource.get(i)
                }
                eventRaised(StreamEvent.RangeAdded(this, start, source.lastIndex - start))
            }
            is StreamEvent.Changed -> {
            }
            is StreamEvent.RangeChanged -> {
            }
            is StreamEvent.Removed -> {
            }
            is StreamEvent.RangeRemoved -> {
            }
            is StreamEvent.Reset -> {
            }
        }
    }
}