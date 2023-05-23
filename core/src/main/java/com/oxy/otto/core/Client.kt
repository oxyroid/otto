package com.oxy.otto.core

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Client {
    private var engine: Engine? = null
    private var source: TaskSource? = null
    private var dispatcher: CoroutineDispatcher = Dispatchers.IO
    suspend fun execute(task: Task) {
        withContext(dispatcher) {
            engine?.executor?.execute(source, task)
        }
    }

    class Builder {
        private val client: Client = Client()
        fun build(): Client {
            return client
        }

        fun setEngine(engine: Engine): Builder {
            client.engine = engine
            return this
        }

        fun setTaskSource(source: TaskSource): Builder {
            client.source = source
            return this
        }

        fun setDispatcher(dispatcher: CoroutineDispatcher): Builder {
            client.dispatcher = dispatcher
            return this
        }
    }
}
