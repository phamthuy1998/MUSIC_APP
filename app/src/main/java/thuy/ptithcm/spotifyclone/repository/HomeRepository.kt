package thuy.ptithcm.spotifyclone.repository

import thuy.ptithcm.spotifyclone.data.*

interface HomeRepository{
    fun getAdvertise(): ResultData<ArrayList<Advertise>>
    fun getSongType(): ResultData<ArrayList<SongType>>
    fun getPlaylist(): ResultData<ArrayList<Playlist>>
    fun getListAlbum(): ResultData<ArrayList<Album>>
    fun getUserInfo(): ResultData<User>
}