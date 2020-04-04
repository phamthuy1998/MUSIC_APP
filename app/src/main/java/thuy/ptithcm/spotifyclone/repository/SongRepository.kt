package thuy.ptithcm.spotifyclone.repository

import thuy.ptithcm.spotifyclone.data.ResultData
import thuy.ptithcm.spotifyclone.data.Song

interface SongRepository{
    fun getSongByID(songID: String): ResultData<Song>
}