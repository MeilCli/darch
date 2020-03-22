package net.meilcli.darch.common.models

import net.meilcli.darch.common.IDarchModel
import net.meilcli.darch.common.INotifyPropertyChanged
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class NotifiablePropertyProvider<TModel : IDarchModel, TValue : Any>(
    private val notifyPropertyChanged: INotifyPropertyChanged,
    private val propertyCollection: IPropertyCollection,
    private val defaultValue: TValue
) {

    private var readonly = false

    fun readonly(): NotifiablePropertyProvider<TModel, TValue> {
        readonly = true
        return this
    }

    operator fun provideDelegate(
        thisRef: TModel,
        property: KProperty<*>
    ): ReadWriteProperty<TModel, TValue> {
        return NotifiableProperty(
            thisRef,
            property,
            notifyPropertyChanged,
            property.name,
            defaultValue,
            readonly
        ).also {
            propertyCollection.add(it)
        }
    }
}