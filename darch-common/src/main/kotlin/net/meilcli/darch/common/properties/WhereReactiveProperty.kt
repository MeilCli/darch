package net.meilcli.darch.common.properties

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import net.meilcli.darch.common.IReadOnlyReactiveProperty

@ExperimentalCoroutinesApi
class WhereReactiveProperty<T>(
    coroutineScope: CoroutineScope,
    source: IReadOnlyReactiveProperty<T>,
    predicate: (T) -> Boolean
) : IReadOnlyReactiveProperty<T> {

    private var isDisposed = false
    private val channel = ConflatedBroadcastChannel<T>()
    private val job: Job

    override var isValueInitialized: Boolean = false
        private set

    init {
        if (source.isValueInitialized) {
            val value = source.value()
            if (predicate(value)) {
                channel.offer(value)
                isValueInitialized = true
            }
        }
        job = coroutineScope.launch {
            source.openSubscription().consumeEach {
                if (predicate(it)) {
                    channel.offer(it)
                    isValueInitialized = true
                }
            }
        }
    }

    override fun value(): T {
        return channel.value
    }

    override fun openSubscription(): ReceiveChannel<T> {
        return channel.openSubscription()
    }

    override fun dispose() {
        if (isDisposed.not()) {
            isDisposed = true
            channel.cancel()
            job.cancelChildren()
        }
    }
}