package net.meilcli.darch

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import net.meilcli.darch.common.DarchViewModel
import net.meilcli.darch.common.IReadOnlyReactiveProperty
import net.meilcli.darch.common.extensions.reactiveProperty
import net.meilcli.darch.common.extensions.readOnlyReactiveProperty
import net.meilcli.darch.common.loggers.ILogger
import net.meilcli.darch.common.parameters.UnitParameter

@ExperimentalCoroutinesApi
class MainViewModel(
    coroutineScope: CoroutineScope,
    logger: ILogger
) : DarchViewModel(coroutineScope, logger) {

    private val model = MainModel(this)

    val textChangeCount: IReadOnlyReactiveProperty<String>

    val text = model.reactiveProperty<MainModel, String> { it::text.name }

    val helloWorldCommand = reactiveCommand<UnitParameter>()

    val wasTextChangedQuery = activeQuery(this::wasTextChanged)

    init {
        val textChangeCountValue = model
            .readOnlyReactiveProperty<MainModel, Int> { it::textChangeCount.name }
            .select { it.toString() } // for testing
        textChangeCount = combineLatest(
            model.readOnlyReactiveProperty<MainModel, String> { it::textChangeCountPrefix.name },
            textChangeCountValue
        ) { prefix, value -> "$prefix: $value" }

        helloWorldCommand.register(this::helloWorld)
    }

    private fun helloWorld(@Suppress("UNUSED_PARAMETER") parameter: UnitParameter) {
        model.helloWorld()
    }

    private fun wasTextChanged(@Suppress("UNUSED_PARAMETER") parameter: UnitParameter): Boolean {
        return 0 < model.textChangeCount
    }
}