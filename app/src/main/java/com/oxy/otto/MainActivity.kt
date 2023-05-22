package com.oxy.otto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.oxy.otto.core.Task
import com.oxy.otto.core.client.Client
import com.oxy.otto.okhttp.OkhttpEngine
import com.oxy.otto.ui.theme.OttoTheme
import java.io.File

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val client = Client.Builder()
            .setEngine(OkhttpEngine)
            .build()
        val file = File(applicationContext.filesDir, "apks")
        if (!file.exists()) file.createNewFile()

        val task = Task.Builder()
            .setUrl("https://s.otto.com/app.apk")
            .setOutput(file.outputStream())
            .setSnapshotReceiver { snapshot ->

            }
            .build()
        client.execute(task)


        setContent {
            OttoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    OttoTheme {
        Greeting("Android")
    }
}