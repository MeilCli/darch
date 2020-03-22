package net.meilcli.darch.common.streams

import net.meilcli.darch.common.INotifyStreamChanged
import net.meilcli.darch.common.IReadOnlyStream
import net.meilcli.darch.common.StreamEvent

class LinearStackStream<T>(
    private val source1: IReadOnlyStream<T>,
    private val source2: IReadOnlyStream<T>
) : IReadOnlyStream<T>, INotifyStreamChanged {

    private val eventNotifiers = mutableListOf<INotifyStreamChanged>()

    override val size: Int
        get() = source1.size + source2.size

    init {
        source1.registerNotifyStreamChanged(this)
        source2.registerNotifyStreamChanged(this)
    }

    override fun get(index: Int): T {
        return when (index) {
            in 0 until source1.size -> source1.get(index)
            in source1.size until size -> source2.get(index - source1.size)
            else -> throw IndexOutOfBoundsException("index $index not in 0 until $size")
        }
    }

    override fun indexOf(element: T): Int {
        var index = source1.indexOf(element)
        if (index < 0) {
            index = source2.indexOf(element)
            if (0 < index) {
                index + source1.size
            }
        }
        return index
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

    override fun eventRaised(event: StreamEvent) = when (event.sender) {
        source1 -> eventRaisedBySource1(event)
        source2 -> eventRaisedBySource2(event)
        else -> Unit
    }

    private fun eventRaisedBySource1(event: StreamEvent) {
        raiseEvent(event.clone(this))
    }

    private fun eventRaisedBySource2(event: StreamEvent) = when (event) {
        is StreamEvent.Added -> raiseEvent(StreamEvent.Added(this, source1.size + event.index))
        is StreamEvent.RangeAdded -> raiseEvent(
            StreamEvent.RangeAdded(
                this,
                source1.size + event.startIndex,
                event.count
            )
        )
        is StreamEvent.Changed -> raiseEvent(StreamEvent.Changed(this, source1.size + event.index))
        is StreamEvent.RangeChanged -> raiseEvent(
            StreamEvent.RangeChanged(
                this,
                source1.size + event.startIndex,
                event.count
            )
        )
        is StreamEvent.Removed -> raiseEvent(StreamEvent.Removed(this, source1.size + event.index))
        is StreamEvent.RangeRemoved -> raiseEvent(
            StreamEvent.RangeRemoved(
                this,
                source1.size + event.startIndex,
                event.count
            )
        )
        is StreamEvent.Reset -> raiseEvent(StreamEvent.Reset(this))
    }
}