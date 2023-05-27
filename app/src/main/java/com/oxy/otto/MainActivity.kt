package com.oxy.otto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.oxy.otto.core.Client
import com.oxy.otto.core.Task
import com.oxy.otto.okhttp.OkhttpEngine
import com.oxy.otto.ui.theme.OttoTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okio.sink
import java.io.File

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val text =
            mutableStateOf(Task.Snapshot(0, 0, 0))

        val client = Client.Builder()
            .setEngine(OkhttpEngine)
            .setDispatcher(Dispatchers.IO)
            .build()
        val file = File(applicationContext.filesDir, "big_buck_bunny.mp4")
        if (!file.exists()) file.createNewFile()

        val task = Task.Builder()
            .setData("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4")
            .setSink(file.outputStream().sink())
            .setSnapshotReceiver { snapshot ->
                text.value = snapshot
            }
            .build()
        lifecycleScope.launch {
            client.execute(task)
        }


        setContent {
            OttoTheme {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("${text.value}")
                }
            }
        }
    }
}
