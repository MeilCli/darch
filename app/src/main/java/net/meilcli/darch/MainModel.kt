package net.meilcli.darch

import net.meilcli.darch.common.DarchModel
import net.meilcli.darch.common.IDarchComponent
import net.meilcli.darch.common.INotifyPropertyChanged
import net.meilcli.darch.common.PropertyEvent

class MainModel(
    darchComponent: IDarchComponent
) : DarchModel(darchComponent), INotifyPropertyChanged {

    // static value for testing
    val textChangeCountPrefix by property("TextChange").readonly()

    var textChangeCount by property(0).readonly()
        private set

    var text by property("")

    init {
        registerNotifyPropertyChanged(this)
    }

    fun helloWorld() {
        text = "Hello World"
    }

    override fun eventRaised(event: PropertyEvent) {
        if (event is PropertyEvent.Named && event.property.name == this::text.name) {
            textChangeCount += 1
        }
    }
}