package balekouy.industries.punchwarrior.presentation

import android.content.Context
import android.media.MediaPlayer
import javax.inject.Singleton


@Singleton
class SoundPlayer(val context: Context) {
    companion object {
        @Volatile
        private var INSTANCE: SoundPlayer? = null

        fun getInstance(context: Context): SoundPlayer =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: setContext(context).also { INSTANCE = it }
            }

        private fun setContext(context: Context) =
            SoundPlayer(context)
    }


    private var player: MediaPlayer? = null

    fun startMusic(musicId: Int) {
        player?.let { if (it.isPlaying) it.stop() }
        player = MediaPlayer.create(context, musicId)
        player?.let {
            it.setVolume(100f, 100f)
            it.start()
        }
    }

    fun pauseMusic() {
        player?.pause()
    }

    fun restartMusic() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun stopMusic() {
        player?.stop()
        player?.let {
            it.stop()
            it.release()
        }
    }
}