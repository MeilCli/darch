package net.meilcli.darch.common

import net.meilcli.darch.common.loggers.ILogger
import net.meilcli.darch.common.models.INotifiableProperty
import net.meilcli.darch.common.models.IPropertyCollection

open class DarchModel(
    private val darchComponent: IDarchComponent
) : IDarchModel {

    private val propertyCollectionSource = PropertyCollection()
    private val notifyPropertyChangedSource = NotifyPropertyChanged()

    override val logger: ILogger
        get() = darchComponent.logger

    override val IDarchModel.notifyPropertyChanged: INotifyPropertyChanged
        get() = notifyPropertyChangedSource

    override val IDarchModel.propertyCollection: IPropertyCollection
        get() = propertyCollectionSource

    override fun registerNotifyPropertyChanged(notifyPropertyChanged: INotifyPropertyChanged) {
        notifyPropertyChangedSource.add(notifyPropertyChanged)
    }

    override fun unregisterNotifyPropertyChanged(notifyPropertyChanged: INotifyPropertyChanged) {
        notifyPropertyChangedSource.remove(notifyPropertyChanged)
    }

    private class PropertyCollection : IPropertyCollection {

        private val source = mutableListOf<INotifiableProperty<*, *>>()

        override fun add(property: INotifiableProperty<*, *>) {
            source += property
        }

        override fun findByName(propertyName: String): INotifiableProperty<*, *> {
            return checkNotNull(source.find { it.propertyName == propertyName }) { "Unknown property on this model" }
        }
    }

    private class NotifyPropertyChanged : INotifyPropertyChanged {

        private val collection = mutableListOf<INotifyPropertyChanged>()

        fun add(notifyPropertyChanged: INotifyPropertyChanged) {
            collection += notifyPropertyChanged
        }

        fun remove(notifyPropertyChanged: INotifyPropertyChanged) {
            collection -= notifyPropertyChanged
        }

        override fun eventRaised(event: PropertyEvent) {
            for (notifyPropertyChanged in collection) {
                notifyPropertyChanged.eventRaised(event)
            }
        }
    }
}