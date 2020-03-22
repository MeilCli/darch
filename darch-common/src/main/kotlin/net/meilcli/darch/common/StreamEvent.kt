package net.meilcli.darch.common

sealed class StreamEvent(val sender: Any) {

    abstract fun clone(sender: Any): StreamEvent

    class Changed(sender: Any, val index: Int) : StreamEvent(sender) {

        override fun clone(sender: Any): StreamEvent {
            return Changed(sender, index)
        }
    }

    class RangeChanged(sender: Any, val startIndex: Int, val count: Int) : StreamEvent(sender) {

        val range: IntRange = startIndex..(startIndex + count)

        override fun clone(sender: Any): StreamEvent {
            return RangeChanged(sender, startIndex, count)
        }
    }

    class Added(sender: Any, val index: Int) : StreamEvent(sender) {

        override fun clone(sender: Any): StreamEvent {
            return Added(sender, index)
        }
    }

    class RangeAdded(sender: Any, val startIndex: Int, val count: Int) : StreamEvent(sender) {

        val range: IntRange = startIndex..(startIndex + count)

        override fun clone(sender: Any): StreamEvent {
            return RangeAdded(sender, startIndex, count)
        }
    }

    class Removed(sender: Any, val index: Int) : StreamEvent(sender) {

        override fun clone(sender: Any): StreamEvent {
            return Removed(sender, index)
        }
    }

    class RangeRemoved(sender: Any, val startIndex: Int, val count: Int) : StreamEvent(sender) {

        val range: IntRange = startIndex..(startIndex + count)

        override fun clone(sender: Any): StreamEvent {
            return RangeRemoved(sender, startIndex, count)
        }
    }

    class Reset(sender: Any) : StreamEvent(sender) {

        override fun clone(sender: Any): StreamEvent {
            return Reset(sender)
        }
    }
}