package thuy.ptithcm.spotifyclone.repository

import androidx.lifecycle.MutableLiveData
import thuy.ptithcm.spotifyclone.data.NetworkState
import thuy.ptithcm.spotifyclone.data.ResultData
import thuy.ptithcm.spotifyclone.data.Song
import thuy.ptithcm.spotifyclone.firebase.FirebaseGetData

class SongRepositoryImpl(private val firebase: FirebaseGetData) : SongRepository {



    override fun getSongByID(songID: String): ResultData<Song> {
        val networkState = MutableLiveData<NetworkState>()
        val responseSong = MutableLiveData<Song>()
        firebase.getSongByID(
            songID,
            onPrepared = {
                networkState.postValue(NetworkState.LOADING)
            },
            onSuccess = { response ->
                responseSong.value = response
                networkState.postValue(NetworkState.LOADED)
            },
            onError = { errMessage ->
                networkState.postValue(NetworkState.error(errMessage))
            }
        )
        return ResultData(
            data = responseSong,
            networkState = networkState
        )
    }


}