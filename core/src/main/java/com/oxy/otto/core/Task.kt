package com.oxy.otto.core

import okio.Sink

class Task private constructor() {
    var data: String? = null
        internal set
    var sink: Sink? = null
        internal set
    var receiver: SnapshotReceiver? = null
        internal set
    var buffer: Long = 1024
        internal set

    class Builder {
        private val task: Task = Task()
        fun build(): Task = task
        fun setData(data: String): Builder = this.apply {
            task.data = data
        }

        fun setSink(sink: Sink): Builder = this.apply {
            task.sink = sink
        }

        fun setSnapshotReceiver(receiver: SnapshotReceiver): Builder = this.apply {
            task.receiver = receiver
        }
    }

    data class Snapshot(
        val total: Long,
        val buffer: Long,
        val contentLength: Long
    )
}

typealias SnapshotReceiver = (Task.Snapshot) -> Unit