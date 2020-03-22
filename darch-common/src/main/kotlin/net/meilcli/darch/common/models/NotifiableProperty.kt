package net.meilcli.darch.common.models

import net.meilcli.darch.common.IDarchModel
import net.meilcli.darch.common.INotifyPropertyChanged
import net.meilcli.darch.common.PropertyEvent
import kotlin.reflect.KProperty

class NotifiableProperty<TModel : IDarchModel, TValue : Any>(
    private val thisRef: TModel,
    override val property: KProperty<*>,
    private val notifyPropertyChanged: INotifyPropertyChanged,
    override val propertyName: String,
    defaultValue: TValue,
    override val readonly: Boolean
) : INotifiableProperty<TModel, TValue> {

    private var propertyValue: TValue = defaultValue

    override fun getValue(thisRef: TModel, property: KProperty<*>): TValue {
        return propertyValue
    }

    override fun setValue(thisRef: TModel, property: KProperty<*>, value: TValue) {
        setValue(value)
    }

    override fun getValue(): TValue {
        return propertyValue
    }

    override fun setValue(value: TValue) {
        if (value != propertyValue) {
            propertyValue = value
            notifyPropertyChanged.eventRaised(PropertyEvent.Named(thisRef, property, propertyName))
        }
    }

    override fun isInitialized(): Boolean {
        return true
    }
}