package thuy.ptithcm.spotifyclone.repository

import thuy.ptithcm.spotifyclone.data.ResultData
import thuy.ptithcm.spotifyclone.data.Song

interface KindOfSongRepository{
    fun getListSongInKind(idSongType: String): ResultData<ArrayList<Song>>
}