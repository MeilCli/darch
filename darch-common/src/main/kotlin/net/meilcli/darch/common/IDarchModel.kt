package net.meilcli.darch.common

import net.meilcli.darch.common.models.IPropertyCollection
import net.meilcli.darch.common.models.LazyNotifiablePropertyProvider
import net.meilcli.darch.common.models.NotifiablePropertyProvider
import net.meilcli.darch.common.models.NullableNotifiablePropertyProvider

interface IDarchModel : IDarchComponent {

    val IDarchModel.notifyPropertyChanged: INotifyPropertyChanged

    val IDarchModel.propertyCollection: IPropertyCollection

    fun <T : Any> IDarchModel.property(defaultValue: T) =
        NotifiablePropertyProvider<IDarchModel, T>(
            notifyPropertyChanged,
            propertyCollection,
            defaultValue
        )

    fun <T : Any> IDarchModel.lazyProperty() = LazyNotifiablePropertyProvider<IDarchModel, T>(
        notifyPropertyChanged,
        propertyCollection
    )

    fun <T> IDarchModel.nullableProperty() = NullableNotifiablePropertyProvider<IDarchModel, T>(
        notifyPropertyChanged,
        propertyCollection
    )

    fun registerNotifyPropertyChanged(notifyPropertyChanged: INotifyPropertyChanged)

    fun unregisterNotifyPropertyChanged(notifyPropertyChanged: INotifyPropertyChanged)
}