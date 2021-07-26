package com.smile.tiketapps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import io.flutter.embedding.android.FlutterActivity
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etFirstName = findViewById<EditText>(R.id.etFirstName)
        val etLastName = findViewById<EditText>(R.id.etLastName)

        val flutterEngine = (application as TiketApplication).tiketFlutterEngine
        val channel =
                MethodChannel(
                        flutterEngine.dartExecutor.binaryMessenger,
                        "tiket/registration_channel"
                )
        channel.setMethodCallHandler { methodCall: MethodCall, result: MethodChannel.Result ->
            if (methodCall.method == "login_user") {
                etFirstName.setText("From Flutter: ${methodCall.arguments}")
            } else {
                result.notImplemented()
            }
        }

        val btnLogin = findViewById<Button>(R.id.btnLogin)

        btnLogin.setOnClickListener {
            channel.invokeMethod("login_user","${etFirstName.text} ${etLastName.text}")
            startActivity(
                    FlutterActivity.withCachedEngine("tiket_engine").build(this)
            )
        }
    }
}