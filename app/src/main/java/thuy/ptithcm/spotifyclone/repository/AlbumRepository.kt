package thuy.ptithcm.spotifyclone.repository

import thuy.ptithcm.spotifyclone.data.Album
import thuy.ptithcm.spotifyclone.data.ResultData
import thuy.ptithcm.spotifyclone.data.Song

interface AlbumRepository{
    fun getListSongOfAlbum(albumID: String): ResultData<ArrayList<Song>>
    fun getAlbumInfoByID(albumID: String): ResultData<Album>
}