package net.meilcli.darch.common.models

import net.meilcli.darch.common.IDarchModel
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

interface INotifiableProperty<TModel : IDarchModel, TValue>
    : ReadWriteProperty<TModel, TValue> {

    val property: KProperty<*>

    val propertyName: String

    val readonly: Boolean

    fun setValue(value: TValue)

    fun getValue(): TValue

    fun isInitialized(): Boolean
}