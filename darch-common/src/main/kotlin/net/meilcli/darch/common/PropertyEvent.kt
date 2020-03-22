package net.meilcli.darch.common

import kotlin.reflect.KProperty

sealed class PropertyEvent(val sender: Any) {

    class Named(sender: Any, val property: KProperty<*>, val propertyName: String) :
        PropertyEvent(sender)

    class Unknown(sender: Any) : PropertyEvent(sender)
}