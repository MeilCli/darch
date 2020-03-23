package net.meilcli.darch.common.properties

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import net.meilcli.darch.common.IReadOnlyReactiveProperty

@ExperimentalCoroutinesApi
class CombineLatestReactiveProperty<TSource1, TSource2, TResult>(
    coroutineScope: CoroutineScope,
    source1: IReadOnlyReactiveProperty<TSource1>,
    source2: IReadOnlyReactiveProperty<TSource2>,
    selector: (TSource1, TSource2) -> TResult
) : IReadOnlyReactiveProperty<TResult> {

    private var isDisposed = false
    private val channel = ConflatedBroadcastChannel<TResult>()
    private val job1: Job
    private val job2: Job

    override var isValueInitialized: Boolean = false
        private set

    init {
        if (source1.isValueInitialized && source2.isValueInitialized) {
            channel.offer(selector(source1.value(), source2.value()))
            isValueInitialized = true
        }
        job1 = coroutineScope.launch {
            source1.openSubscription().consumeEach {
                if (source2.isValueInitialized) {
                    channel.offer(selector(it, source2.value()))
                    isValueInitialized = true
                }
            }
        }
        job2 = coroutineScope.launch {
            source2.openSubscription().consumeEach {
                if (source1.isValueInitialized) {
                    channel.offer(selector(source1.value(), it))
                    isValueInitialized = true
                }
            }
        }
    }

    override fun value(): TResult {
        return channel.value
    }

    override fun openSubscription(): ReceiveChannel<TResult> {
        return channel.openSubscription()
    }

    override fun dispose() {
        if (isDisposed.not()) {
            isDisposed = true
            channel.cancel()
            job1.cancelChildren()
            job2.cancelChildren()
        }
    }
}