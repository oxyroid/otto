package com.oxy.otto.core.client

import com.oxy.otto.core.Engine
import com.oxy.otto.core.Task

class Client {
    private var engine: Engine? = null
    fun execute(task: Task) {
        engine?.executor?.execute(task)
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
    }
}
