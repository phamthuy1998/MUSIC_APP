package thuy.ptithcm.spotifyclone.repository

import androidx.lifecycle.MutableLiveData
import thuy.ptithcm.spotifyclone.data.NetworkState
import thuy.ptithcm.spotifyclone.data.ResultData
import thuy.ptithcm.spotifyclone.data.User
import thuy.ptithcm.spotifyclone.firebase.FirebaseGetData

class UserRepositoryImpl(private val firebase: FirebaseGetData) : UserRepository {

    override fun getUserInfo(): ResultData<User> {
        val networkState = MutableLiveData<NetworkState>()
        val responseListSong = MutableLiveData<User>()
        firebase.getUserInfo(
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

    override fun signOut(): ResultData<Boolean> {
        val networkState = MutableLiveData<NetworkState>()
        firebase.signOut(
            onPrepared = {
                networkState.postValue(NetworkState.LOADING)
            },
            onSuccess = {
                networkState.postValue(NetworkState.LOADED)
            }
        )
        return ResultData(
            networkState = networkState
        )
    }


}