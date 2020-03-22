package net.meilcli.darch.common.extensions

import kotlinx.coroutines.ExperimentalCoroutinesApi
import net.meilcli.darch.common.*
import net.meilcli.darch.common.models.INotifiableProperty

@ExperimentalCoroutinesApi
inline fun <TModel : IDarchModel, TValue> TModel.reactiveProperty(crossinline propertyNameSelector: (TModel) -> String): IReactiveProperty<TValue> {
    return reactiveProperty(propertyNameSelector(this))
}

@ExperimentalCoroutinesApi
fun <TModel : IDarchModel, TValue> TModel.reactiveProperty(propertyName: String): IReactiveProperty<TValue> {
    @Suppress("UNCHECKED_CAST")
    val notifiableProperty =
        propertyCollection.findByName(propertyName) as INotifiableProperty<TModel, TValue>
    if (notifiableProperty.readonly) {
        throw IllegalStateException("Cannot translate read only property to ReactiveProperty")
    }
    val reactiveProperty = ReactiveProperty(notifiableProperty)
    registerNotifyPropertyChanged(reactiveProperty)
    return reactiveProperty
}

@ExperimentalCoroutinesApi
inline fun <TModel : IDarchModel, TValue> TModel.readOnlyReactiveProperty(crossinline propertyNameSelector: (TModel) -> String): IReadOnlyReactiveProperty<TValue> {
    return readOnlyReactiveProperty(propertyNameSelector(this))
}

@ExperimentalCoroutinesApi
fun <TModel : IDarchModel, TValue> TModel.readOnlyReactiveProperty(propertyName: String): IReadOnlyReactiveProperty<TValue> {
    @Suppress("UNCHECKED_CAST")
    val notifiableProperty =
        propertyCollection.findByName(propertyName) as INotifiableProperty<TModel, TValue>
    val reactiveProperty = ReadOnlyReactiveProperty(notifiableProperty)
    registerNotifyPropertyChanged(reactiveProperty)
    return reactiveProperty
}