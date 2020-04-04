package thuy.ptithcm.spotifyclone.repository

import thuy.ptithcm.spotifyclone.data.ResultData
import thuy.ptithcm.spotifyclone.data.Song

interface FavoriteSongRepository{
    fun getListFavoriteSong(): ResultData<ArrayList<Song>>
}