package com.example.noties.common.utils

import android.app.Service
import android.content.Intent
import android.media.AudioDeviceInfo
import android.media.AudioManager
import android.os.Bundle
import android.os.IBinder
import android.speech.tts.TextToSpeech
import android.util.Log
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SpeechService : Service() {

    private lateinit var tts: TextToSpeech
    private  var context =  this
    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val title = intent.getStringExtra(NOTE_TITLE)
        serviceScope.launch {
            Log.d("DIMA","${isHeadphonesPlugged()}")
            if (isHeadphonesPlugged()) {
                tts = TextToSpeech(context) {
                    if (it == TextToSpeech.SUCCESS) {
                        tts.voice = tts.voices.find { it.name == "en-us-x-iog-local" } ?: tts.defaultVoice
                        tts.speak(title, TextToSpeech.QUEUE_ADD, Bundle(), "utteranceId")
                    }
                }
            }

        }

        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        tts.shutdown()
    }

    private fun isHeadphonesPlugged(): Boolean {
        val audioManager = getSystemService(AUDIO_SERVICE) as AudioManager
        val audioDevices = audioManager.getDevices(AudioManager.GET_DEVICES_ALL)
        for (deviceInfo in audioDevices) {
            if (deviceInfo.type == AudioDeviceInfo.TYPE_WIRED_HEADPHONES
                || deviceInfo.type == AudioDeviceInfo.TYPE_WIRED_HEADSET
                || deviceInfo.type == AudioDeviceInfo.TYPE_BLE_HEADSET
                || deviceInfo.type == AudioDeviceInfo.TYPE_BLE_SPEAKER
                || deviceInfo.type == AudioDeviceInfo.TYPE_BLUETOOTH_A2DP
                || deviceInfo.type == AudioDeviceInfo.TYPE_BLUETOOTH_SCO

            ) {
                return true
            }
        }
        return false
    }

}