package thuy.ptithcm.spotifyclone.repository

import thuy.ptithcm.spotifyclone.data.ResultData
import thuy.ptithcm.spotifyclone.data.Song

interface HistorySongRepository{
    fun getHistoryListSong(): ResultData<ArrayList<Song>>
}