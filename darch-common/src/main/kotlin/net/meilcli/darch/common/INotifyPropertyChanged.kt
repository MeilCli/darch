package net.meilcli.darch.common

interface INotifyPropertyChanged {

    fun eventRaised(event: PropertyEvent)
}