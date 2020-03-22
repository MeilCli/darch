package net.meilcli.darch.common

interface INotifyStreamChanged {

    fun eventRaised(event: StreamEvent)
}