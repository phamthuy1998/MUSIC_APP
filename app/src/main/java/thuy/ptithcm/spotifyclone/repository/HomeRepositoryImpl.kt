package thuy.ptithcm.spotifyclone.repository

import androidx.lifecycle.MutableLiveData
import thuy.ptithcm.spotifyclone.data.*
import thuy.ptithcm.spotifyclone.firebase.FirebaseGetData

class HomeRepositoryImpl(private val firebase: FirebaseGetData) : HomeRepository {
    override fun getAdvertise(): ResultData<ArrayList<Advertise>> {
        val networkState = MutableLiveData<NetworkState>()
        val responseAdvertise = MutableLiveData<ArrayList<Advertise>>()
        firebase.getAdvertise(
            onPrepared = {
                networkState.postValue(NetworkState.LOADING)
            },
            onSuccess = { response ->
                responseAdvertise.value = response ?: arrayListOf()
                networkState.postValue(NetworkState.LOADED)
            },
            onError = { errMessage ->
                networkState.postValue(NetworkState.error(errMessage))
            }
        )
        return ResultData(
            data = responseAdvertise,
            networkState = networkState
        )
    }

    override fun getSongType(): ResultData<ArrayList<SongType>> {
        val networkState = MutableLiveData<NetworkState>()
        val responseSongType = MutableLiveData<ArrayList<SongType>>()
        firebase.getSongType(
            onPrepared = {
                networkState.postValue(NetworkState.LOADING)
            },
            onSuccess = { response ->
                responseSongType.value = response ?: arrayListOf()
                networkState.postValue(NetworkState.LOADED)
            },
            onError = { errMessage ->
                networkState.postValue(NetworkState.error(errMessage))
            }
        )
        return ResultData(
            data = responseSongType,
            networkState = networkState
        )
    }

    override fun getPlaylist(): ResultData<ArrayList<Playlist>> {
        val networkState = MutableLiveData<NetworkState>()
        val responseSongType = MutableLiveData<ArrayList<Playlist>>()
        firebase.getPlaylist(
            onPrepared = {
                networkState.postValue(NetworkState.LOADING)
            },
            onSuccess = { response ->
                responseSongType.value = response ?: arrayListOf()
                networkState.postValue(NetworkState.LOADED)
            },
            onError = { errMessage ->
                networkState.postValue(NetworkState.error(errMessage))
            }
        )
        return ResultData(
            data = responseSongType,
            networkState = networkState
        )
    } override fun getListAlbum(): ResultData<ArrayList<Album>> {
        val networkState = MutableLiveData<NetworkState>()
        val responseSongType = MutableLiveData<ArrayList<Album>>()
        firebase.getListAlbum(
            onPrepared = {
                networkState.postValue(NetworkState.LOADING)
            },
            onSuccess = { response ->
                responseSongType.value = response ?: arrayListOf()
                networkState.postValue(NetworkState.LOADED)
            },
            onError = { errMessage ->
                networkState.postValue(NetworkState.error(errMessage))
            }
        )
        return ResultData(
            data = responseSongType,
            networkState = networkState
        )
    }

    override fun getUserInfo(): ResultData<User> {
        val networkState = MutableLiveData<NetworkState>()
        val responseUser  = MutableLiveData<User>()
        firebase.getUserInfo(
            onPrepared = {
                networkState.postValue(NetworkState.LOADING)
            },
            onSuccess = { response ->
                responseUser.value = response
                networkState.postValue(NetworkState.LOADED)
            },
            onError = { errMessage ->
                networkState.postValue(NetworkState.error(errMessage))
            }
        )
        return ResultData(
            data = responseUser,
            networkState = networkState
        )
    }

}