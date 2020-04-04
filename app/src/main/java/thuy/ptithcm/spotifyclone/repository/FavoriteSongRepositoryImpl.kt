package thuy.ptithcm.spotifyclone.repository

import androidx.lifecycle.MutableLiveData
import thuy.ptithcm.spotifyclone.data.NetworkState
import thuy.ptithcm.spotifyclone.data.ResultData
import thuy.ptithcm.spotifyclone.data.Song
import thuy.ptithcm.spotifyclone.firebase.FirebaseGetData

class FavoriteSongRepositoryImpl(private val firebase: FirebaseGetData) : FavoriteSongRepository {

    override fun getListFavoriteSong(): ResultData<ArrayList<Song>> {
        val networkState = MutableLiveData<NetworkState>()
        val responseListFavoriteSong = MutableLiveData<ArrayList<Song>>()
        firebase.getListFavoriteSong(
            onPrepared = {
                networkState.postValue(NetworkState.LOADING)
            },
            onSuccess = { response ->
                responseListFavoriteSong.value = response
                networkState.postValue(NetworkState.LOADED)
            },
            onError = { errMessage ->
                networkState.postValue(NetworkState.error(errMessage))
            }
        )
        return ResultData(
            data = responseListFavoriteSong,
            networkState = networkState
        )
    }


}