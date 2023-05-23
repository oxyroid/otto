# OTTO - Kotlin File Download Framework

[![](https://jitpack.io/v/thxbrop/otto.svg)](https://jitpack.io/#thxbrop/otto)

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

1. Add it in your root build.gradle at the end of repositories:

```groovy
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

2. Add the dependency:

```groovy
dependencies {
  implementation 'com.github.thxbrop:otto:${latest_version}'
}
```