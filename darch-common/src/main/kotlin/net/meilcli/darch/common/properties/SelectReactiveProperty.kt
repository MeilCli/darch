package net.meilcli.darch.common.properties

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import net.meilcli.darch.common.IReadOnlyReactiveProperty

@ExperimentalCoroutinesApi
class SelectReactiveProperty<TSource, TResult>(
    coroutineScope: CoroutineScope,
    source: IReadOnlyReactiveProperty<TSource>,
    selector: (TSource) -> TResult
) : IReadOnlyReactiveProperty<TResult> {

    private var isDisposed = false
    private val channel = ConflatedBroadcastChannel<TResult>()
    private val job: Job

    init {
        job = coroutineScope.launch {
            source.openSubscription().consumeEach {
                channel.offer(selector(it))
            }
        }
    }

    override fun openSubscription(): ReceiveChannel<TResult> {
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