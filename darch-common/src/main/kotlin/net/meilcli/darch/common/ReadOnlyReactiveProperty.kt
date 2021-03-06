package net.meilcli.darch.common

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.channels.ReceiveChannel
import net.meilcli.darch.common.models.INotifiableProperty

@ExperimentalCoroutinesApi
class ReadOnlyReactiveProperty<TModel : IDarchModel, TValue>(
    private val notifiableProperty: INotifiableProperty<TModel, TValue>
) : IReadOnlyReactiveProperty<TValue>, INotifyPropertyChanged {

    private var isDisposed = false

    private val source = ConflatedBroadcastChannel<TValue>()

    override var isValueInitialized: Boolean = false
        private set

    init {
        if (notifiableProperty.isInitialized()) {
            source.offer(notifiableProperty.getValue())
            isValueInitialized = true
        }
    }

    override fun value(): TValue {
        return source.value
    }

    override fun eventRaised(event: PropertyEvent) {
        when (event) {
            is PropertyEvent.Named -> if (notifiableProperty.isInitialized() &&
                event.propertyName == notifiableProperty.propertyName &&
                source.valueOrNull != notifiableProperty.getValue()
            ) {
                source.offer(notifiableProperty.getValue())
                isValueInitialized = true
            }
            is PropertyEvent.Unknown -> if (notifiableProperty.isInitialized() &&
                source.valueOrNull != notifiableProperty.getValue()
            ) {
                source.offer(notifiableProperty.getValue())
                isValueInitialized = true
            }
        }
    }

    override fun openSubscription(): ReceiveChannel<TValue> {
        return source.openSubscription()
    }

    override fun dispose() {
        if (isDisposed.not()) {
            isDisposed = true
            source.cancel()
        }
    }

}