package net.meilcli.darch.common

import net.meilcli.darch.common.loggers.ILogger
import org.koin.core.KoinComponent

interface IDarchComponent : KoinComponent {

    val logger: ILogger
}