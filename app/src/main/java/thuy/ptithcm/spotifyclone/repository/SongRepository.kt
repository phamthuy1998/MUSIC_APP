package thuy.ptithcm.spotifyclone.repository

import androidx.lifecycle.MutableLiveData
import thuy.ptithcm.spotifyclone.data.NetworkState
import thuy.ptithcm.spotifyclone.data.ResultData
import thuy.ptithcm.spotifyclone.data.Song

interface SongRepository {
    fun getSongByID(songID: String): ResultData<Song>
    fun addSongIntoHistory(song: Song)
    fun checkSongFavorite(songID: String): MutableLiveData<Boolean>
    fun addSongIntoFavorite(song: Song): MutableLiveData<NetworkState>
    fun removeFavoriteSong(songID: String): MutableLiveData<NetworkState>
}