package thuy.ptithcm.spotifyclone.service

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.IBinder


class SoundService : Service() {

    companion object {
        private var instance: SoundService? = null
        fun getInstance() = instance ?: SoundService()
    }

    private var mediaPlayer: MediaPlayer? = null
    private var uri: String = ""

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        uri = intent?.getStringExtra("uriSong") ?: ""
        playAudio(uri)
        return START_STICKY
    }

    private fun playAudio(uri: String) {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(this, Uri.parse(uri))
        }
        mediaPlayer?.setOnCompletionListener { onDestroy() }
        mediaPlayer?.setOnPreparedListener {
            mediaPlayer?.start()
        }
    }

    override fun onCreate() {
        super.onCreate()
    }


    override fun onDestroy() {
        super.onDestroy()
        if (mediaPlayer != null) {
            try {
                mediaPlayer?.stop()
                mediaPlayer?.reset()
                mediaPlayer?.release()
                mediaPlayer = null
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}