# OTTO - Kotlin File Download Framework

## How to use?

```kotlin
val client = Client.Builder()
    .setEngine(OkhttpEngine)
    .setTaskSource(AndroidTaskSource(applicationContext))
    .setDispatcher(Dispatchers.IO)
    .build()

val file = File(applicationContext.filesDir, "video.mp4")
if (!file.exists()) file.createNewFile()

val task = Task.Builder()
    .setData("http://yourwebsite.com/video.mp4")
    .setOutput(file.outputStream())
    .setSnapshotReceiver { snapshot ->
        // subscribe snapshots during the task.
    }
    .build()
coroutineScope.launch {
    client.execute(task)
}
```

## Download
