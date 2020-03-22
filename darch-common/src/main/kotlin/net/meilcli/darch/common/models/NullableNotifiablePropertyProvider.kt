package net.meilcli.darch.common.models

import net.meilcli.darch.common.IDarchModel
import net.meilcli.darch.common.INotifyPropertyChanged
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class NullableNotifiablePropertyProvider<TModel : IDarchModel, TValue>(
    private val notifyPropertyChanged: INotifyPropertyChanged,
    private val propertyCollection: IPropertyCollection
) {

    private var readonly = false

    fun readonly(): NullableNotifiablePropertyProvider<TModel, TValue> {
        readonly = true
        return this
    }

    operator fun provideDelegate(
        thisRef: TModel,
        property: KProperty<*>
    ): ReadWriteProperty<TModel, TValue?> {
        return NullableNotifiableProperty<TModel, TValue>(
            thisRef,
            property,
            notifyPropertyChanged,
            property.name,
            readonly
        ).also {
            propertyCollection.add(it)
        }
    }
}