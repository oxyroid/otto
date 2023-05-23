package com.oxy.otto.core

import java.io.FileOutputStream

class Task private constructor() {
    var data: String? = null
        internal set
    var output: FileOutputStream? = null
        internal set
    var receiver: SnapshotReceiver? = null
        internal set
    var buffer: Int = 1024
        internal set
    var singleton: Boolean = false
        internal set

    class Builder {
        private val task: Task = Task()
        fun build(): Task = task
        fun setData(data: String): Builder = this.apply {
            task.data = data
        }

        fun setOutput(output: FileOutputStream): Builder = this.apply {
            task.output = output
        }

        fun setSnapshotReceiver(
            buffer: Int = 1024,
            receiver: SnapshotReceiver
        ): Builder = this.apply {
            task.buffer = buffer
            task.receiver = receiver
        }

        fun setSingleton(singleton: Boolean): Builder = this.apply {
            task.singleton = singleton
        }
    }

    data class Snapshot(
        val total: Long,
        val count: Int,
        val contentLength: Long
    )
}

typealias SnapshotReceiver = (Task.Snapshot) -> Unit