package com.smile.tiketapps

import android.app.Application
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor

class TiketApplication: Application() {
    lateinit var tiketFlutterEngine: FlutterEngine
    override fun onCreate() {
        super.onCreate()

        // Instantiate a FlutterEngine.
        tiketFlutterEngine = FlutterEngine(this)

        // Start executing Dart code to pre-warm the FlutterEngine.
        tiketFlutterEngine.dartExecutor.executeDartEntrypoint(
            DartExecutor.DartEntrypoint.createDefault()
        )

        // Cache the FlutterEngine to be used by FlutterActivity.
        FlutterEngineCache
            .getInstance()
            .put("tiket_engine", tiketFlutterEngine)
    }
}