package net.meilcli.darch.common.models

interface IPropertyCollection {

    fun add(property: INotifiableProperty<*, *>)

    fun findByName(propertyName: String): INotifiableProperty<*, *>
}