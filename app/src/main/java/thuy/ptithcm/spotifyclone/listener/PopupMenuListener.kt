package thuy.ptithcm.spotifyclone.listener

import android.content.Context
import thuy.ptithcm.spotifyclone.data.Song

interface PopupMenuListener {

    fun play(song: Song)

    fun goToAlbum(song: Song)

    fun goToArtist(song: Song)

    fun addToPlaylist(context: Context, song: Song)

    fun playNext(song: Song)
}
