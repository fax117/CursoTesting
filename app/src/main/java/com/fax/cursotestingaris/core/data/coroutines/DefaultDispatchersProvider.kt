package com.fax.cursotestingaris.core.data.coroutines

import com.fax.cursotestingaris.core.domain.coroutines.DispatchersProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class DefaultDispatchersProvider @Inject constructor() : DispatchersProvider {
    override val main: CoroutineDispatcher = Dispatchers.Main
    override val io: CoroutineDispatcher = Dispatchers.IO
    override val default: CoroutineDispatcher = Dispatchers.Default
}
