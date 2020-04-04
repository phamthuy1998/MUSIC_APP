package thuy.ptithcm.spotifyclone.repository

import androidx.lifecycle.MutableLiveData
import thuy.ptithcm.spotifyclone.data.Album
import thuy.ptithcm.spotifyclone.data.NetworkState
import thuy.ptithcm.spotifyclone.data.ResultData
import thuy.ptithcm.spotifyclone.data.Song
import thuy.ptithcm.spotifyclone.firebase.FirebaseGetData

class AlbumRepositoryImpl(private val firebase: FirebaseGetData) : AlbumRepository {

    override fun getListSongOfAlbum(albumID: String): ResultData<ArrayList<Song>> {
        val networkState = MutableLiveData<NetworkState>()
        val responseListSong = MutableLiveData<ArrayList<Song>>()
        firebase.getListSong(
            albumID,
            "albumID",
            onPrepared = {
                networkState.postValue(NetworkState.LOADING)
            },
            onSuccess = { response ->
                responseListSong.value = response
                networkState.postValue(NetworkState.LOADED)
            },
            onError = { errMessage ->
                networkState.postValue(NetworkState.error(errMessage))
            }
        )
        return ResultData(
            data = responseListSong,
            networkState = networkState
        )
    }

    override fun getAlbumInfoByID(albumID: String): ResultData<Album> {
        val networkState = MutableLiveData<NetworkState>()
        val responseListSong = MutableLiveData<Album>()
        firebase.getAlbumInfoByID(
            albumID,
            onPrepared = {
                networkState.postValue(NetworkState.LOADING)
            },
            onSuccess = { response ->
                responseListSong.value = response
                networkState.postValue(NetworkState.LOADED)
            },
            onError = { errMessage ->
                networkState.postValue(NetworkState.error(errMessage))
            }
        )
        return ResultData(
            data = responseListSong,
            networkState = networkState
        )
    }


}