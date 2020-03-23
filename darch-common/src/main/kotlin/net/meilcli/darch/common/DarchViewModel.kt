package net.meilcli.darch.common

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import net.meilcli.darch.common.loggers.ILogger

@ExperimentalCoroutinesApi
open class DarchViewModel(
    override val coroutineScope: CoroutineScope,
    override val logger: ILogger
) : IDarchViewModel