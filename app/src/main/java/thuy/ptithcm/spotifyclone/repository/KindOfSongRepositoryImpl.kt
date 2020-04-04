package thuy.ptithcm.spotifyclone.repository

import androidx.lifecycle.MutableLiveData
import thuy.ptithcm.spotifyclone.data.NetworkState
import thuy.ptithcm.spotifyclone.data.ResultData
import thuy.ptithcm.spotifyclone.data.Song
import thuy.ptithcm.spotifyclone.firebase.FirebaseGetData

class KindOfSongRepositoryImpl(private val firebase: FirebaseGetData) : KindOfSongRepository {

    override fun getListSongInKind(idSongType: String): ResultData<ArrayList<Song>> {
        val networkState = MutableLiveData<NetworkState>()
        val responseListSong = MutableLiveData<ArrayList<Song>>()
        firebase.getListSong(
            idSongType,
            "typeID",
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