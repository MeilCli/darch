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

    init {
        job = coroutineScope.launch {
            source.openSubscription().consumeEach {
                if (predicate(it)) {
                    channel.offer(it)
                }
            }
        }
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